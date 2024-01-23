package com.project.adminproxy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.adminproxy.security.AuthContextService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.adminproxy.exception.MakerInvalidException;
import com.project.adminproxy.model.MakerRequestRequestData;
import com.project.adminproxy.model.MakerRequestResponseData;
import com.project.adminproxy.model.UpdateStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class MakerCheckerService {
    @Value("${com.project.adminproxy.aws.makerCheckerAPIGateway}")
    private String makerCheckerAPIGatewayUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WriteLogsService writeLogsService;
    public final AuthContextService authService;

    @Autowired
    public MakerCheckerService(WriteLogsService writeLogsService, AuthContextService authService) {
        this.writeLogsService = writeLogsService;
        this.authService = authService;
    }

    /**
     * MakerCreateFunction
     * - Desc: Create request for approval
     * - Method: POST
     * - Path: /create-request
     * - Parameters needed: makerUser (maker name), request_data (message [optional]), checkerUser (checker name), checkerEmail, (to be discussed), makerRole, checkerRole, actionType (Points/User)
     * */
    public String makerCreateFunction(MakerRequestRequestData makerRequestRequestData, HttpServletRequest servletRequest) {
        try {
            String url = makerCheckerAPIGatewayUrl + "/create-request";
            String requestBody = objectMapper.writeValueAsString(makerRequestRequestData);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            log.info("Sending request");
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("got response");
            if (response.statusCode() != 201) {
                throw new MakerInvalidException("Unable to create request. Returned status code of: " + response.statusCode());
            }

            //TODO: logs

            return response.body();
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new MakerInvalidException("Unable to create request");
        }
    }

    /**
     *  MakerReadFunction
     * - Desc: to read/get request
     * - Method: GET
     * - Path: /view-request/{RequestID}
     * - Parameters needed: None
     * */
    public MakerRequestResponseData makerReadFunction(String requestId, HttpServletRequest servletRequest) {
        try {
            String url = makerCheckerAPIGatewayUrl + "/view-request/" + requestId;
            log.info("url = " + url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            log.info("created request to maker read function");

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("response received from maker read function");
            if (response.statusCode() != 200) {
                throw new MakerInvalidException("Unable to retrieve request with id = " + requestId);
            }

            MakerRequestResponseData responseEntity = objectMapper.readValue(response.body(), MakerRequestResponseData.class);
            //TODO: logs
            return responseEntity;

        } catch (Exception e) {
            log.error("Error getting maker-checker request", e);
            log.error("Error getting maker-checker request: " + e.getMessage());
            return null;
        }
    }

    /**
     *  MakerDeleteFunction
     * - Desc: to delete request
     * - Method: DELETE
     * - Path: /update-request/{RequestID}
     * - Parameters needed: None
     * */
    public String makerDeleteFunction(String requestId, HttpServletRequest servletRequest) {
        try {
            String url = makerCheckerAPIGatewayUrl + "/update-request/" + requestId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();
            log.info("created request to maker delete function.");

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("response received from maker delete function");
            if (response.statusCode() != 200) {
                throw new MakerInvalidException("Unable to retrieve request with id = " + requestId);
            }

            //TODO: logs

            return response.body();
        } catch (Exception e) {
            throw new MakerInvalidException("Unable to retrieve request with id = " + requestId);
        }
    }

    /**
     * CheckerFunction
     * - Desc: to approve request
     * - Method: PUT
     * - Path: /view-request/{RequestID}
     * - Parameters needed: new_status
     * */
    public String checkerFunction(String requestId, UpdateStatus newStatus, HttpServletRequest servletRequest) {
        try {
            String url = makerCheckerAPIGatewayUrl + "/view-request/" + requestId;
            String requestBody = objectMapper.writeValueAsString(newStatus);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new MakerInvalidException("No request with id = " + requestId);
            }

            //TODO: logs

            return response.body();
        } catch (Exception e) {
            throw new MakerInvalidException("Unable to retrieve request with id = " + requestId);
        }
    }
}
