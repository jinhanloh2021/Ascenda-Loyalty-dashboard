package com.project.adminproxy.service;

import java.util.UUID;

import com.project.adminproxy.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.adminproxy.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = User.builder().userId(UUID.randomUUID().toString()).name(request.getName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role("Default").build(); // set default role on signup
        userService.createUser(user);
        userService.setUserPermissions(user);
        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse.builder().token(jwt).build();

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse adminSignUp(SignUpRequest request) {
        User user = User.builder().userId(UUID.randomUUID().toString()).name(request.getName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role("Default").build(); // set default role on signup
        userService.setUserPermissions(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse adminSignUp(SignUpRequest request, String roleName) {
        User user = User.builder().userId(UUID.randomUUID().toString()).name(request.getName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(roleName).build(); // set default role on signup
        userService.setUserPermissions(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        log.info("signing in...");
        // throws if unauthenticated
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByEmail(request.getEmail());
        userService.setUserPermissions(user);
        log.info("user with email successfully retrieved");
        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}