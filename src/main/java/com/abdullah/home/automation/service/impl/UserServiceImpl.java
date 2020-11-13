package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.User;
import com.abdullah.home.automation.domain.security.PasswordResetToken;
import com.abdullah.home.automation.domain.security.UserRole;
import com.abdullah.home.automation.repository.PasswordResetTokenRepository;
import com.abdullah.home.automation.repository.RoleRepository;
import com.abdullah.home.automation.repository.UserRepository;
import com.abdullah.home.automation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        Optional<User> localUser = userRepository.findByUsername(user.getUsername());

        if (localUser.isPresent()) {
            log.warn("user {} already exists. Nothing will be done.", user.getUsername());
//			throw new Exception("user already exists. Nothing will be done");
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            return userRepository.save(user);
            //localUser =
        }

        log.error("Db save operation failed");
        throw new Exception("Db save operation failed");
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return user;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
