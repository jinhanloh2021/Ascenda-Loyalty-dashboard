package com.project.adminproxy.controller;

import com.project.adminproxy.service.AuthenticationService;
import com.project.adminproxy.service.UserService;
import com.project.adminproxy.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.project.adminproxy.model.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/app/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("hasAuthority('user.create')")
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody PostUserRequest postUserRequest, HttpServletRequest request) {
        log.info("Creating new user");
        SignUpRequest signUpRequest = new SignUpRequest(postUserRequest.getName(), postUserRequest.getEmail());
        boolean gotRole = false;
        if (postUserRequest.getRoleName() == null) {
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.adminSignUp(signUpRequest);
        } else {
            log.info("creating with role:" + postUserRequest.getRoleName());
            gotRole = true;
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.adminSignUp(signUpRequest, postUserRequest.getRoleName());
        }

        userService.createUser(Utils.convertToUser(postUserRequest, gotRole), request);
        PostUserResponse postUserResponse = new PostUserResponse();
        postUserResponse.setName(signUpRequest.getName());
        postUserResponse.setEmail(signUpRequest.getEmail());
        postUserResponse.setPassword(signUpRequest.getPassword());
        log.info("New user created successfully");
        return new ResponseEntity<>(postUserResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping()
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        log.info("Getting all users");
        List<User> retrievedUsers = userService.getAllUsers(request);
        log.info("All users successfully retrieved");
        return ResponseEntity.ok(retrievedUsers);
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId, HttpServletRequest request) {
        log.info("Retrieving User with id = " + userId);
        User retrievedUser = userService.getUserById(userId, request);
        log.info("User with id = " + userId + " retrieved successfully");
        return ResponseEntity.ok(retrievedUser);
    }

    @PreAuthorize("hasAuthority('user.update')")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser,
            HttpServletRequest request) {
        log.info("Updating User with id = " + userId);
        User newUpdatedUser = userService.updateUser(userId, updatedUser, request);
        log.info("Successfully updated User with id = " + userId);
        return ResponseEntity.ok(newUpdatedUser);
    }

    @PreAuthorize("hasAuthority('user.delete')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId, HttpServletRequest request) {
        log.info("Deleting User with id = " + userId);
        User deletedUser = userService.deleteUser(userId, request);
        log.info("Successfully deleted User with id = " + userId);
        return ResponseEntity.ok(deletedUser);
    }
}
