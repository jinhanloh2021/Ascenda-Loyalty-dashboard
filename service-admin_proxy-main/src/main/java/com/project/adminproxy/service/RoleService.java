package com.project.adminproxy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.adminproxy.model.Role;
import com.project.adminproxy.security.AuthContextService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.UUID;
import java.util.List;
import java.util.Arrays;

@Service
@Slf4j
public class RoleService {
    @Value("${com.project.adminproxy.aws.roleAPIGateway}")
    private String roleAPIGatewayUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WriteLogsService writeLogsService;
    public final AuthContextService authService;

    @Autowired
    public RoleService(AuthContextService authService, WriteLogsService writeLogsService) {
        this.authService = authService;
        this.writeLogsService = writeLogsService;
    }

    public List<Role> getAllRoles(HttpServletRequest servletRequest) {
        try {
            String url = roleAPIGatewayUrl + "/role";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Role[] rolesArray = objectMapper.readValue(response.body(), Role[].class);

            String executor = authService.getExecutorUsername();
            writeLogsService.successfulGetAllRoles(executor, servletRequest);
            return Arrays.asList(rolesArray);

        } catch (Exception e) {
            log.error("Error getting all roles: " + e.getMessage());
            return null;
        }
    }

    public List<Role> getAllRoles() {
        try {
            log.info("Getting all roles");
            String url = roleAPIGatewayUrl + "/role";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Role[] rolesArray = objectMapper.readValue(response.body(), Role[].class);

            return Arrays.asList(rolesArray);

        } catch (Exception e) {
            log.error("Error getting all roles: " + e.getMessage());
            return null;
        }
    }

    public Role getRoleById(UUID roleId, HttpServletRequest servletRequest) {
        try {
            String url = roleAPIGatewayUrl + "/role/" + roleId.toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String executor = authService.getExecutorUsername();

            writeLogsService.successfulGetRole(objectMapper.readValue(response.body(), Role.class), executor,
                    servletRequest);
            return objectMapper.readValue(response.body(), Role.class);

        } catch (Exception e) {
            log.error("Error getting role by ID: " + e.getMessage());
            return null;
        }
    }

    public Role getRoleByName(String roleName) {
        List<Role> allRoles = getAllRoles();
        for (Role r : allRoles) {
            if (r.getRoleName().equals(roleName)) {
                return r;
            }
        }
        log.error("Error getting role by name: " + roleName);
        // Role not found
        return new Role();
    }

    public Role createRole(Role role, HttpServletRequest servletRequest) {
        try {
            String url = roleAPIGatewayUrl + "/role";
            String requestBody = objectMapper.writeValueAsString(role);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String executor = authService.getExecutorUsername();
            writeLogsService.successfulRoleCreation(role, executor, servletRequest);

            return objectMapper.readValue(response.body(), Role.class);
        } catch (Exception e) {
            log.error("Error creating role: " + e.getMessage());
            return null;
        }
    }

    public Role updateRole(UUID roleId, Role newRole, HttpServletRequest servletRequest) {
        try {
            String url = roleAPIGatewayUrl + "/role/" + roleId.toString();
            String requestBody = objectMapper.writeValueAsString(newRole);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String executor = authService.getExecutorUsername();
            writeLogsService.successfulRoleUpdate(newRole, executor, servletRequest);

            return objectMapper.readValue(response.body(), Role.class);
        } catch (Exception e) {
            log.error("Error updating role: " + e.getMessage());
            return null;
        }
    }

    public Boolean deleteRole(UUID roleId, HttpServletRequest servletRequest) {
        try {
            String url = roleAPIGatewayUrl + "/role/" + roleId.toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Failed to delete role. Status code: " + response.statusCode());
                return false;
            }

            String executor = authService.getExecutorUsername();
            writeLogsService.successfulRoleDeletion(objectMapper.readValue(response.body(), Role.class), executor,
                    servletRequest);

            return true;
        } catch (Exception e) {
            log.error("Error deleting role: " + e.getMessage());
            return false;
        }
    }

}
