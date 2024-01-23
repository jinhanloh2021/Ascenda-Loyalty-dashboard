package com.project.adminproxy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected")
@Slf4j
public class ProtectedController {
    // No preauthorize. As long as logged in can visit /protected
    @GetMapping()
    public ResponseEntity<?> getProtected() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.toString());
        return ResponseEntity.ok(auth);
    }
}
