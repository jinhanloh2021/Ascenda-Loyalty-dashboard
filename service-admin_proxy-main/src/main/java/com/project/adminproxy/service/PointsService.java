package com.project.adminproxy.service;

import com.project.adminproxy.exception.UserNotFoundException;
import com.project.adminproxy.model.Points;
import javax.validation.Valid;

import com.project.adminproxy.model.User;
import com.project.adminproxy.security.AuthContextService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;

@Service
@Slf4j
public class PointsService {
    private final WebClient webClient;
    private final WriteLogsService writeLogsService;
    private final AuthContextService authService;
    private final UserService userService;

    @Autowired
    public PointsService(WebClient.Builder webClientBuilder, WriteLogsService writeLogsService,
            AuthContextService authService, UserService userService) {
        this.webClient = webClientBuilder
                .baseUrl("http://points-service-nlb-40e2a3fb1af9187b.elb.ap-southeast-1.amazonaws.com:8003/points")
                .build();
        // this.webClient =
        // webClientBuilder.baseUrl("http://localhost:8000/points").build();
        this.writeLogsService = writeLogsService;
        this.authService = authService;
        this.userService = userService;
    }

    public Points createPoints(@Valid Points points, HttpServletRequest request) {
        // validate whether userID belongs to a valid user
        String userId = points.getUserId();
        // Check if the user with the specified userId exists
        User existingUser = userService.getUserById(userId, request);

        if (existingUser == null) {
            // Handle the case where the user does not exist
            log.warn("User with userId {} does not exist.", userId);
            // You may throw an exception, return an error response, or handle it according
            // to your requirements.
            // For now, let's assume you want to throw an exception.
            throw new UserNotFoundException("User with userId " + userId + " does not exist.");
        }

        String executor = authService.getExecutorUsername();
        Points createdPoints = webClient.post()
                .bodyValue(points)
                .retrieve()
                .bodyToMono(Points.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved POST response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulPointsCreation(executor, request);
                })
                .block();

        writeLogsService.successfulPointsCreation(createdPoints, executor, request);
        return createdPoints;
    }

    public List<Points> getAllPoints(HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        List<Points> allPoints = webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Points>>() {
                })
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulAllPointsRetrieval(executor, request);
                })
                .block();

        writeLogsService.successfulAllPointsRetrieval(executor, request);
        return allPoints;
    }

    public Points getPoints(String pointsId, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        Points retrievedPoints = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{pointsId}").build(pointsId))
                .retrieve()
                .bodyToMono(Points.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulPointsRetrieval(executor, request);
                })
                .block();

        writeLogsService.successfulPointsRetrieval(retrievedPoints, executor, request);
        return retrievedPoints;
    }

    public Points updatePoints(String pointsId, @Valid Points newPoints, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        Points updatedPoints = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("/{pointsId}").build(pointsId))
                .bodyValue(newPoints)
                .retrieve()
                .bodyToMono(Points.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved PUT response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulPointsUpdate(executor, request);
                })
                .block();

        writeLogsService.successfulPointsUpdate(updatedPoints, executor, request);
        return updatedPoints;
    }

    public Points deletePoints(String pointsId, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        Points deletedPoints = webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/{pointsId}").build(pointsId))
                .retrieve()
                .bodyToMono(Points.class)
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved DELETE response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulPointsDeletion(executor, request);
                })
                .block();

        writeLogsService.successfulPointsDeletion(deletedPoints, executor, request);
        return deletedPoints;
    }
}
