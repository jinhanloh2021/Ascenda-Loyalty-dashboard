package com.project.adminproxy.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.adminproxy.utils.Utils;

@RestController
@Slf4j
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<String> healthCheck(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ipAddress = Utils.getClientIpAddress(request);
        log.info(request.getRemoteAddr());
        log.info(userAgent);
        log.info("Admin proxy app health check successful!");
        return new ResponseEntity<>("Health Check successful", HttpStatus.OK);
    }
}
