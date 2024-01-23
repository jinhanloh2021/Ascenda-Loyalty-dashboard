package com.project.adminproxy.controller;

import com.project.adminproxy.model.MakerRequestRequestData;
import com.project.adminproxy.model.MakerRequestResponseData;
import com.project.adminproxy.model.UpdateStatus;
import com.project.adminproxy.service.MakerCheckerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maker-checker")
@Slf4j
public class MakerCheckerController {
    private MakerCheckerService makerCheckerService;

    @Autowired
    public MakerCheckerController(MakerCheckerService makerCheckerService) {
        this.makerCheckerService = makerCheckerService;
    }

    @PostMapping
    public ResponseEntity<?> makerCreate(@RequestBody MakerRequestRequestData makerRequestRequestData, HttpServletRequest servletRequest) {
        log.info("Creating maker request");
        String response = makerCheckerService.makerCreateFunction(makerRequestRequestData, servletRequest);
        log.info("Successfully created maker request");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<?> makerRead(@PathVariable String requestId, HttpServletRequest request) {
        log.info("Reading maker function");
        MakerRequestResponseData retrievedMakerRequestResponseData = makerCheckerService.makerReadFunction(requestId, request);
        log.info("Successfully retrieved maker checker request of id = " + requestId);
        return ResponseEntity.ok(retrievedMakerRequestResponseData);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<?> makerDelete(@PathVariable String requestId, HttpServletRequest request) {
        log.info("Deleting maker request");
        String deleteRequest = makerCheckerService.makerDeleteFunction(requestId, request);
        log.info("Successfully deleted maker checker request of id = " + requestId);
        return ResponseEntity.ok(deleteRequest);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<?> checkerFunction(@PathVariable String requestId, @RequestBody UpdateStatus updateStatus, HttpServletRequest request) {
        log.info("Updating status of checkerFunction");
        String deleteRequest = makerCheckerService.checkerFunction(requestId, updateStatus, request);
        log.info("Successfully updated maker checker request of id = " + requestId);
        return ResponseEntity.ok(deleteRequest);
    }

}
