package com.app.elect.auth.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.app.elect.auth.database.repository.UserRepository;
import com.app.elect.auth.dto.AuthRequest;
import com.app.elect.auth.dto.JwtResponse;
import com.app.elect.auth.mapper.UserCreateMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;

    public JwtResponse signUp(AuthRequest authRequest) {
        return Optional.of(authRequest)
            .map(userCreateMapper::map)
            .map(userRepository::save)
            .map(jwtService::generateToken)
            .get();
    }

    public JwtResponse signIn(AuthRequest authRequest) throws Exception {
        return Optional.of(authRequest)
            .map(req -> userDetailsServiceImpl.loadUserByUsername(req.getUsername()))
            .map(user -> {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user, 
                    authRequest.getPassword()
                ));
                return user;
            })
            .map(jwtService::generateToken)
            .orElseThrow(() -> new Exception("Invalid username or password"));
    }

}
