package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.User;
import com.abdullah.home.automation.domain.security.PasswordResetToken;
import com.abdullah.home.automation.service.AuthService;
import com.abdullah.home.automation.service.UserService;
import com.abdullah.home.automation.utlity.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class AuthController {

    private final UserService userService;

    private final AuthService authService;
    @Autowired
    public AuthController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }


    @PostMapping(value = "/newUser")
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
                              @ModelAttribute("username") String username, Model model) throws Exception {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("usernameExists", true);

            return "myAccount";
        }

        if (userService.findByEmail(userEmail).isPresent()) {
            model.addAttribute("emailExists", true);

            return "myAccount";
        }

        boolean registration = authService.registration(username, userEmail, request);
        if (registration) {
            model.addAttribute("emailSent", "true");
        } else {
            model.addAttribute("emailSent", "false");
        }

        return "myAccount";
    }

    @GetMapping("/newUser")
    public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if (passToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        User user = authService.setContext(passToken);

        model.addAttribute("user", user);
        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

        model.addAttribute("classActiveForgetPassword", true);

        Optional<User> optionalUser = userService.findByEmail(email);

        if (!optionalUser.isPresent()) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }else{

            boolean forgetPassword = authService.forgetPassword(optionalUser.get(),request);
            if (forgetPassword){
                model.addAttribute("forgetPasswordEmailSent", "true");
            }else {
                model.addAttribute("forgetPasswordEmailSent", "false");
            }

            return "myAccount";
        }
    }

    @PostMapping(value = "/updateUserInfo")
    public String updateUserInfo(@ModelAttribute("user") User user, @ModelAttribute("newPassword") String newPassword,
                                 Model model) throws Exception {
        Optional<User> currentUser = userService.findById(user.getId());

        if (!currentUser.isPresent()) {
            throw new Exception("User not found");
        } else {
            User currentUserFromDb = currentUser.get();


            /* check email already exists */
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                if (!userService.findByEmail(user.getEmail()).get().getId().equals(currentUserFromDb.getId())) {
                    model.addAttribute("emailExists", true);
                    return "myProfile";
                }
            }

            /* check username already exists */
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                if (!userService.findByUsername(user.getUsername()).get().getId().equals(currentUserFromDb.getId())) {
                    model.addAttribute("usernameExists", true);
                    return "myProfile";
                }
            }

            // update password
            if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
                BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
                String dbPassword = currentUserFromDb.getPassword();
                if (passwordEncoder.matches(user.getPassword(), dbPassword)) {
                    currentUserFromDb.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    model.addAttribute("incorrectPassword", true);
                    return "myProfile";
                }
            }

            boolean updateUser = authService.updateUser(currentUserFromDb,user, currentUser.get().getUsername());
            if (updateUser){
                model.addAttribute("updateSuccess", true);
                model.addAttribute("user", currentUserFromDb);
                model.addAttribute("classActiveLogin", true);
            } else {
                model.addAttribute("updateSuccess", false);
//                model.addAttribute("user", currentUserFromDb);
                model.addAttribute("classActiveLogin", false);
            }

            return "redirect:/";
        }
    }
}
