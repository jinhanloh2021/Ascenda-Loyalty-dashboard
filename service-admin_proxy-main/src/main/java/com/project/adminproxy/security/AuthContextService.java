package com.project.adminproxy.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.adminproxy.model.User;

@Service
@Slf4j
public class AuthContextService {
    public String getExecutorUsername() {
        log.info("getting executor name");
        String username = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            username = user.getUserId();
        } catch (Exception e) {
            username = "Internal";
        }
        log.info("got the executor name");
        return username;
    }

    // get role
    public String getExecutorRole() {
        log.info("getting executor role");
        String role = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            role = user.getRole();
        } catch (Exception e) {
            role = "Default";
        }
        log.info("got the executor role");
        return role;
    }

}
