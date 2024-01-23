package com.Project.UserService.Controller;

import com.Project.UserService.Model.User;
import com.Project.UserService.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info("Creating new user");
        User newUser = userService.createUser(user);
        log.info("New user created successfully");
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        log.info("Getting all users");
        List<User> retrievedUsers = userService.getAllUsers();
        log.info("All users successfully retrieved");
        return ResponseEntity.ok(retrievedUsers);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        log.info("Retrieving User with id = " + userId);
        User retrievedUser = userService.getUser(userId);
        log.info("User with id = " + userId + " retrieved successfully");
        return ResponseEntity.ok(retrievedUser);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("Retrieving User with email = " + email);
        User retrievedUser = userService.getUserByEmail(email);
        log.info("User with email = " + email + " retrieved successfully");
        return ResponseEntity.ok(retrievedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        log.info("Updating User with id = " + userId);
        User newUpdatedUser = userService.updateUser(userId, updatedUser);
        log.info("Successfully updated User with id = " + userId);
        return ResponseEntity.ok(newUpdatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId) {
        log.info("Deleting User with id = " + userId);
        User deletedUser = userService.deleteUser(userId);
        log.info("Successfully deleted User with id = " + userId);
        return ResponseEntity.ok(deletedUser);
    }

}
