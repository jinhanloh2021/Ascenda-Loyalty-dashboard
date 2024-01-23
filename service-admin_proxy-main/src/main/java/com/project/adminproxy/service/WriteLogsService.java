package com.project.adminproxy.service;

import com.project.adminproxy.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.adminproxy.model.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Slf4j
public class WriteLogsService {

    private final SqsClient sqsClient;

    @Autowired
    public WriteLogsService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public SendMessageResponse sendLogMessageToSQS(SqsClient sqsClient, String messageBody) {
        log.info("Sending log message to SQS");
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl("https://sqs.ap-southeast-1.amazonaws.com/699089610166/logs_store_queues")
                .messageBody(messageBody)
                .build();

        SendMessageResponse sendMessageResponse = sqsClient.sendMessage(sendMessageRequest);
        log.info("Message sent. Message ID: " + sendMessageResponse.messageId());
        return sendMessageResponse;
    }

    /**
     * User logs
     */
    public void successfulUserCreation(User user, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "created user ";
        String message = executor + " " + action + user.getName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulUserCreation(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to create user";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulUserCreation(User user, String executor) {
        String device = "Internal server";
        String action = "Internal create user ";
        String message = executor + " " + action + user.getName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulGetUser(User user, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved user ";
        String message = executor + " " + action + user.getName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulGetUser(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to retrieve user";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulGetUser(User user, String executor) {
        String device = "Internal server";
        String action = "Internal retrieved user ";
        String message = executor + " " + action + user.getName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulGetAllUser(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved all users information";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulGetAllUser(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to retrieve all users information";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulUpdateUser(User updatedUser, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "updated user ";
        String message = executor + " " + action + updatedUser.getName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulUpdateUser(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to update user";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulDeleteUser(User deletedUser, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "deleted user ";
        String message = executor + " " + action + deletedUser.getName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulDeleteUser(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to delete user";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    /**
     * Role logs
     */
    public void successfulRoleCreation(Role role, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "created role ";
        String message = executor + " " + action + role.getRoleName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulGetRole(Role role, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved role ";
        String message = executor + " " + action + role.getRoleName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulGetAllRoles(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved all roles";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulRoleUpdate(Role role, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "updated role ";
        String message = executor + " " + action + role.getRoleName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulRoleDeletion(Role role, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "deleted role ";
        String message = executor + " " + action + role.getRoleName();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    /**
     * Points logs
     */
    public void successfulPointsCreation(Points points, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "created points account ";
        String message = executor + " " + action + points.toString();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulPointsCreation(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to create points account ";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulPointsRetrieval(Points points, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved points account ";
        String message = executor + " " + action + points.toString();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulPointsRetrieval(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to retrieve points account";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulAllPointsRetrieval(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved all points accounts";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulAllPointsRetrieval(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to retrieve all points accounts";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulPointsUpdate(Points points, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "Updated points account ";
        String message = executor + " " + action + points.toString();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulPointsUpdate(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to update points account";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulPointsDeletion(Points points, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "deleted points account ";
        String message = executor + " " + action + points.toString();
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulPointsDeletion(String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to delete points account";
        String message = executor + " " + action;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    /**
     * UserPoints logs
     */

    public void successfulGetUserPoints(String userId, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved points accounts for user with ID ";
        String message = executor + " " + action + userId;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulGetUserPoints(String userId, String executor, HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to retrieve points accounts for user with ID ";
        String message = executor + " " + action + userId;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void successfulGetUserPointByApp(String userId, String appName, String executor,
            HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "retrieved points for app ";
        String message = executor + " " + action + appName + " for user with ID " + userId;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

    public void unsuccessfulGetUserPointByApp(String userId, String appName, String executor,
                                            HttpServletRequest request) {
        String device = request.getHeader("User-Agent") + Utils.getClientIpAddress(request);
        String action = "failed to retrieve points for app ";
        String message = executor + " " + action + appName + " for user with ID " + userId;
        String messageBody = "Device:" + device + ",Message:" + message;
        sendLogMessageToSQS(sqsClient, messageBody);
    }

}

// need to create a message for each of the different actions
// deal all the connection and actual sending to