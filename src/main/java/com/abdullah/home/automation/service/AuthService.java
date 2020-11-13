package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.User;
import com.abdullah.home.automation.domain.security.PasswordResetToken;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    boolean registration(String username, String userEmail, HttpServletRequest request);

    User setContext(PasswordResetToken passToken);

    boolean forgetPassword(User user, HttpServletRequest request);

    boolean updateUser(User currentUserFromDb,User user, String username);
}
