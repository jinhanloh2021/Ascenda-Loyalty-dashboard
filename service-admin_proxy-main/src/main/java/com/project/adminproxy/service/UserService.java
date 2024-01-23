package com.project.adminproxy.service;

import com.project.adminproxy.model.*;
import com.project.adminproxy.security.AuthContextService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final WebClient webClient;
    private final WriteLogsService writeLogsService;
    private final RoleService roleService;
    public final AuthContextService authService;

    @Autowired
    public UserService(WebClient.Builder webClientBuilder, WriteLogsService writeLogsService,
            AuthContextService authService, RoleService roleService) {
        this.webClient = webClientBuilder
                .baseUrl("http://user-service-nlb-51a1afffa13dd135.elb.ap-southeast-1.amazonaws.com:8002/users")
                .build();
        // http://user-service-nlb-51a1afffa13dd135.elb.ap-southeast-1.amazonaws.com:8002/users
        // http://localhost:8002/users
        this.writeLogsService = writeLogsService;
        this.authService = authService;
        this.roleService = roleService;
    }

    /**
     * 
     * Admin logs in to frontend, and enrols a user. He "Signs up" for someone else.
     * Internally call sign up. Generate credentials password, return password.
     */
    public User createUser(@Valid User user, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        User createdUser = webClient.post()
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved POST response from User app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulUserCreation(executor, request);
                })
                .block();

        writeLogsService.successfulUserCreation(createdUser, executor, request);
        return createdUser;
    }

    public User createUser(@Valid User user) {

        log.info("Creating new user:" + user);

        User createdUser = webClient.post()
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .block();
//         String executor = authService.getExecutorUsername();
//         writeLogsService.successfulUserCreation(createdUser, executor);
        return createdUser;
    }

    public List<User> getAllUsers(HttpServletRequest request) {

        String executor = authService.getExecutorUsername();
        List<User> allUsers = webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {
                })
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from User app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulGetAllUser(executor, request);
                })
                .block();
        log.info("got the users");

         writeLogsService.successfulGetAllUser(executor, request);
        return allUsers;
    }

    public User getUserById(String userId, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        User user = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{userId}").build(userId))
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from User app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulUserCreation(executor, request);
                })
                .block();

        log.info("This is the get user: " + user.toString());


         writeLogsService.successfulGetUser(user, executor, request);
        return user;
    }

    public User getUserByEmail(String email, HttpServletRequest request) {
        User user = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/email/{email}").build(email))
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from User app");
                })
                .doOnError(WebClientResponseException.class, ex -> {

                })
                .block();

        log.info("This is the get user: " + user.toString());

        String executor = authService.getExecutorUsername();
         writeLogsService.successfulGetUser(user, executor, request);
        return user;
    }

    /** todo: getUserByEmail with request */

    public User getUserByEmail(String email) {
        User user = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/email/{email}").build(email))
                .retrieve()
                .bodyToMono(User.class)
                .block();

         String executor = authService.getExecutorUsername();
         writeLogsService.successfulGetUser(user, executor);
        return user;
    }

    public User updateUser(String userId, @Valid User user, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        User updatedUser = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("/{userId}").build(userId))
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved PUT response from User app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulUpdateUser(executor, request);
                })
                .block();


        writeLogsService.successfulUpdateUser(updatedUser, executor, request);
        return updatedUser;
    }

    public User deleteUser(String userId, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        User deletedUser = webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/{userId}").build(userId))
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved DELETE response from User app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulDeleteUser(executor, request);
                })
                .block();

        writeLogsService.successfulDeleteUser(deletedUser, executor, request);
        return deletedUser;
    }

    /**
     * Sets List<String> permissions on user object. Which will then be converted to
     * GrantedAuthorities when the Collection<Authorities> getAuthorities() method
     * is called.
     * Role is get from User. Permissions get from Role. Then permissions set on
     * User.
     * 
     * @Internal
     * @param Role        Admin, containing permissions on each service
     * @param permissions ['user.create', 'user.read', 'logs.read']
     * 
     * @param user        User Object to set permissions on
     * @return User Object with populated permissions based on Role
     */
    public void setUserPermissions(User user) {
        log.info("Setting permissions on user");
        // 1. Get role
        String roleName = user.getRole();
        Role role = roleService.getRoleByName(roleName);

        // 2. From role, get permissions for each Service
        List<String> roleAuthorities = role.getRoleAuthorities();
        roleAuthorities.add(roleName);

        // 3. Set List<GrantedAuthorities> permissions on User
        log.info("Authorities: " + Arrays.toString(roleAuthorities.toArray()));
        log.info("Authorities: " + roleAuthorities.toString());
        user.setPermissions(roleAuthorities);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) {
                try {
                    User user = getUserByEmail(email);
                    setUserPermissions(user); // set permissions based on Role
                    return user;
                } catch (Exception e) {
                    throw new UsernameNotFoundException("User not found");
                }
            }
        };
    }
}
