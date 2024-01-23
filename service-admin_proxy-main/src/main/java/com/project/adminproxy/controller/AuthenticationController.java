package com.project.adminproxy.controller;

import com.project.adminproxy.model.PostUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.adminproxy.model.SignInRequest;
import com.project.adminproxy.model.SignUpRequest;
import com.project.adminproxy.model.JwtAuthenticationResponse;
import com.project.adminproxy.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/app/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        log.info("sign up attempt");
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        log.info("login attempt");
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(request);
        log.info("login successful");
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }
}