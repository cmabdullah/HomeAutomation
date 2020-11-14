package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.User;
import com.abdullah.home.automation.domain.security.PasswordResetToken;
import com.abdullah.home.automation.domain.security.Role;
import com.abdullah.home.automation.domain.security.UserRole;
import com.abdullah.home.automation.service.AuthService;
import com.abdullah.home.automation.service.UserSecurityService;
import com.abdullah.home.automation.service.UserService;
import com.abdullah.home.automation.utlity.MailConstructor;
import com.abdullah.home.automation.utlity.SecurityUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final JavaMailSender mailSender;

    private final MailConstructor mailConstructor;

    private final UserService userService;

    private final UserSecurityService userSecurityService;

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);


    @Autowired
    public AuthServiceImpl(JavaMailSender mailSender, MailConstructor mailConstructor,
                           UserService userService, UserSecurityService userSecurityService) {
        this.mailSender = mailSender;
        this.mailConstructor = mailConstructor;
        this.userService = userService;
        this.userSecurityService = userSecurityService;

    }

    @Override
    public boolean registration(String username, String userEmail, HttpServletRequest request) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();
        log.info("system generated password "+password);

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));

        try {
            userService.createUser(user, userRoles);

            createPasswordTokenAndSendMail(user, password, request);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean forgetPassword(User user, HttpServletRequest request) {

        String password = SecurityUtility.randomPassword();
        log.info("system generated password "+password);
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

        user.setPassword(encryptedPassword);

        User updateInfo = userService.save(user);

        if (updateInfo != null) {
            createPasswordTokenAndSendMail(user, password, request);
            return true;
        } else {
            return false;
        }

    }

    private void createPasswordTokenAndSendMail(User user, String password, HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
                password);

        mailSender.send(newEmail);
    }

    @Override
    public User setContext(PasswordResetToken passToken) {

        User user = passToken.getUser();
        String username = user.getUsername();

        setAuthenticationContext(username);

        return user;
    }

    @Override
    public boolean updateUser(User currentUserFromDb, User user, String username) {

        currentUserFromDb.setFirstName(user.getFirstName());
        currentUserFromDb.setLastName(user.getLastName());
        currentUserFromDb.setUsername(user.getUsername());
        currentUserFromDb.setEmail(user.getEmail());

        User updateUser = userService.save(currentUserFromDb);

        if (updateUser != null) {
            setAuthenticationContext(username);
            return true;
        } else {
            return false;
        }
    }

    private void setAuthenticationContext(String username) {
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
