package com.project.adminproxy.service;

import com.project.adminproxy.model.Points;
import com.project.adminproxy.security.AuthContextService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
public class UserPointsService {
    private final WebClient webClient;

    private final WriteLogsService writeLogsService;
    public final AuthContextService authService;

    @Autowired
    public UserPointsService(WebClient.Builder webClientBuilder, AuthContextService authService, WriteLogsService writeLogsService) {
        this.webClient = webClientBuilder.baseUrl("http://points-service-nlb-40e2a3fb1af9187b.elb.ap-southeast-1.amazonaws.com:8003/points").build();
        this.authService = authService;
        this.writeLogsService = writeLogsService;
    }
    public List<Points> getUserAllPoints(String userId, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        List<Points> userPoints = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users/{userId}").build(userId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Points>>() {
                })
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulGetUserPoints(userId, executor, request);
                })
                .block();

        writeLogsService.successfulGetUserPoints(userId, executor, request);

        return userPoints;
    }

    public List<Points> getUserPointByApp(String userId, String appName, HttpServletRequest request) {
        String executor = authService.getExecutorUsername();
        List<Points> userPoint = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users/{userId}/{appName}").build(userId, appName))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Points>>() {
                })
                .doOnSuccess(response -> {
                    log.info("Successfully retrieved GET response from Points app");
                })
                .doOnError(WebClientResponseException.class, ex -> {
                    writeLogsService.unsuccessfulGetUserPointByApp(userId, appName, executor, request);
                })
                .block();

        writeLogsService.successfulGetUserPointByApp(userId, appName, executor, request);

        return userPoint;
    }
}
