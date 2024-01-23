package com.project.adminproxy.controller;

import com.project.adminproxy.model.Points;
import com.project.adminproxy.service.UserPointsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/app/users")
public class UserPointsController {

    private final UserPointsService userPointsService;

    @Autowired
    public UserPointsController(UserPointsService userPointsService) {
        this.userPointsService = userPointsService;
    }

    @PreAuthorize("hasAuthority('points.read') and hasAuthority('user.read')")
    @GetMapping("/{userId}/points")
    public ResponseEntity<List<Points>> getUserPoints(@PathVariable String userId, HttpServletRequest request) {
        log.info("Retrieving points with user of id = " + userId);
        List<Points> userPoints = userPointsService.getUserAllPoints(userId, request);
        log.info("Successfully retrieved all points account of user of id = " + userId);
        return ResponseEntity.ok(userPoints);
    }

    @PreAuthorize("hasAuthority('points.read') and hasAuthority('user.read')")
    @GetMapping("/{userId}/points/{appName}")
    public ResponseEntity<List<Points>> getUserPoint(@PathVariable String userId, @PathVariable String appName,
            HttpServletRequest request) {
        log.info("Retrieving points with user of id = " + userId + " for " + appName);
        List<Points> userPoint = userPointsService.getUserPointByApp(userId, appName, request);
        log.info("Successfully retrieved Points for user of id = " + userId + " for app " + appName);
        return ResponseEntity.ok(userPoint);
    }
}
