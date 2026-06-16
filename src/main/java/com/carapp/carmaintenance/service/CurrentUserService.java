package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Utilizator neautentificat.");
        }

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Utilizatorul autentificat nu exista.")
                );
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}