package com.project.adminproxy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.adminproxy.model.LogsReturn;
import com.project.adminproxy.model.S3QueryRequest;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ReadLogsService {
    private final S3Client s3Client;

    @Autowired
    public ReadLogsService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<LogsReturn> queryAllLogs() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket("project-g2t3b-logs")
                .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        List<S3Object> s3Objects = listObjectsV2Response.contents();

        return convertToLogsReturn(s3Objects);
    }


    @PreDestroy
    public void onDestroy() {
        log.info("Closing S3Client during application shutdown.");
        s3Client.close();
    }

    public List<LogsReturn> queryLogsWithParams(int offset, int limit) {
        log.info("offset:" + offset + ",limit:" + limit);

        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket("project-g2t3b-logs")
                .build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsRequest);

        // Retrieve and process the response, considering the latest N objects
        List<S3Object> objects = new ArrayList<>(listObjectsV2Response.contents());

        // Sort the objects in reverse order based on your criteria (e.g., timestamps)
        objects.sort(Comparator.comparing(S3Object::lastModified).reversed());

        // Take the objects within the specified offset and limit
        List<S3Object> filteredObjects = objects.subList(offset, offset + limit);
        log.info("num logs retrieved: " + filteredObjects.size());

        return convertToLogsReturn(filteredObjects);
    }

    private List<LogsReturn> convertToLogsReturn(List<S3Object> contents) {
        log.info("Number of objects in the bucket: " + contents.stream().count());
        List<LogsReturn> logsReturns = new ArrayList<>();
        for (S3Object s3Object : contents) {
            String key = s3Object.key();

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("project-g2t3b-logs")
                    .key(key)
                    .build();
            ResponseInputStream<GetObjectResponse> s3objectResponse = s3Client
                    .getObject(getObjectRequest);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3objectResponse))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Parse the JSON string
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(line);

                    // Extract the value of the "body" key
                    String body = jsonNode.get("Records").get(0).get("body").asText();
                    LogsReturn logsReturn = createLogsReturnObject(body);
                    logsReturn.setLogsId(jsonNode.get("Records").get(0).get("messageId").asText());
                    logsReturn.setDateTime(jsonNode.get("Records").get(0).get("attributes").get("SentTimestamp").asText());
                    logsReturns.add(logsReturn);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logsReturns;
    }

    private LogsReturn createLogsReturnObject(String body) {
        log.info(body);
        String[] fields = body.split(",");
        LogsReturn logsReturn = new LogsReturn();
        log.info(fields[0].split(":")[1]);
        logsReturn.setDevice(fields[0].split(":")[1]);
        log.info(fields[1].split(":")[1]);
        logsReturn.setDescription(fields[1].split(":")[1]);
        return logsReturn;
    }
}
