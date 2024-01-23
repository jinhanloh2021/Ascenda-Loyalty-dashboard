package com.project.adminproxy.controller;

import com.project.adminproxy.model.Points;
import com.project.adminproxy.model.PostPointsRequest;
import com.project.adminproxy.service.PointsService;
import com.project.adminproxy.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/v1/app/points")
public class PointsController {
    private final PointsService pointsService;
    @Autowired
    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @PreAuthorize("hasAuthority('points.create')")
    @PostMapping()
    public ResponseEntity<?> createPoints(@RequestBody PostPointsRequest points, HttpServletRequest request) {
        log.info("Creating new points item");
        Points result = pointsService.createPoints(Utils.convertToPoints(points), request);
        log.info("New points item created successfully");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('points.read')")
    @GetMapping()
    public ResponseEntity<List<Points>> getAllPoints(HttpServletRequest request) {
        log.info("Getting all Points items");
        List<Points> retrievedPoints = pointsService.getAllPoints(request);
        log.info("All Points items successfully retrieved");
        return ResponseEntity.ok(retrievedPoints);
    }

    @PreAuthorize("hasAuthority('points.read')")
    @GetMapping("/{pointsId}")
    public ResponseEntity<Points> getPoints(@PathVariable String pointsId, HttpServletRequest request) {
        log.info("Retrieving Points item with id = " + pointsId);
        Points retrievedPoints = pointsService.getPoints(pointsId, request);
        log.info("Points item with id = " + pointsId + " retrieved successfully");
        return ResponseEntity.ok(retrievedPoints);
    }

    @PreAuthorize("hasAuthority('points.update')")
    @PutMapping("/{pointsId}")
    public ResponseEntity<Points> updatePoints(@PathVariable String pointsId, @RequestBody Points newPoints, HttpServletRequest request) {
        log.info("Updating Points item with id = " + pointsId);
        Points updatedPoints = pointsService.updatePoints(pointsId, newPoints, request);
        log.info("Successfully updated Points item with id = " + pointsId);
        return ResponseEntity.ok(updatedPoints);
    }

    @PreAuthorize("hasAuthority('points.delete')")
    @DeleteMapping("/{pointsId}")
    public ResponseEntity<Points> deletePoints(@PathVariable String pointsId, HttpServletRequest request) {
        log.info("Deleting Points item with id = " + pointsId);
        Points deletedPoints = pointsService.deletePoints(pointsId, request);
        log.info("Successfully deleted Points item with id = " + pointsId);
        return ResponseEntity.ok(deletedPoints);
    }
}
