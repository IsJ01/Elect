package com.app.elect.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.app.elect.auth.database.entity.Role;
import com.app.elect.auth.database.entity.User;
import com.app.elect.auth.database.repository.UserRepository;
import com.app.elect.auth.dto.AuthRequest;
import com.app.elect.auth.service.AuthenticationService;
import com.app.elect.auth.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitCommandLiner implements CommandLineRunner {

    private final UserDetailsServiceImpl userService;
    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    
    @Value("${admin.pass}")
    private String ADMIN_PASS;

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.loadUserByUsername("Admin");
        } catch (UsernameNotFoundException e) {
            AuthRequest signUp = new AuthRequest(
                "Admin", 
                ADMIN_PASS
            );
            authenticationService.signUp(signUp);
            User user = userRepository.findByUsername("Admin").get();
            user.setRole(Role.ADMIN);
            userRepository.saveAndFlush(user);
        }
    }

}
