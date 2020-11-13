package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.User;
import com.abdullah.home.automation.domain.security.PasswordResetToken;
import com.abdullah.home.automation.domain.security.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findByUsername(String username);
    User save(User user);
    List<User> findAll();

    PasswordResetToken getPasswordResetToken(final String token);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    Optional<User>  findByEmail(String userEmail);

    void createPasswordResetTokenForUser(final User user, final String token);

    Optional<User>  findById(Long id);
}
