package com.project.adminproxy.utils;

import com.project.adminproxy.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class Utils {
    public static String maskEmail(String email) {
        String[] parts = email.split("@", 2); // Split the email at the @ symbol
        String maskedEmail = parts[0].substring(0, 1) + parts[0].substring(1).replaceAll(".", "*") + "@" + parts[1];
        return maskedEmail;
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        // The X-Forwarded-For header is commonly used to retrieve the original IP address
        String xForwardedFor = request.getHeader("X-Forwarded-For");

        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            // The client's IP address will be the first entry in the X-Forwarded-For header
            return xForwardedFor.split(",")[0].trim();
        }

        // If X-Forwarded-For header is not present, fallback to the standard method
        return request.getRemoteAddr();
    }

    public static User convertToUser(PostUserRequest postUserRequest, boolean gotRole) {
        User user = new User();
        if (gotRole) {
            user.setRole(postUserRequest.getRoleName());
        } else {
            user.setRole("Default");
        }
        user.setName(postUserRequest.getName());
        user.setEmail(postUserRequest.getEmail());
        return user;
    }

    public static Points convertToPoints(PostPointsRequest postPointsRequest) {
        Points points = new Points();
        points.setAppName(postPointsRequest.getAppName());
        points.setBalance(postPointsRequest.getBalance());
        points.setUserId(postPointsRequest.getUserId());
        return points;
    }

}
