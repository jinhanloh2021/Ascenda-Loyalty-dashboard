package com.project.adminproxy.controller;

import com.project.adminproxy.model.LogsReturn;
import com.project.adminproxy.model.S3QueryRequest;
import com.project.adminproxy.service.ReadLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/v1/app/logs")
public class LogsController {
    private ReadLogsService readLogsService;

    public LogsController(ReadLogsService readLogsService) {
        this.readLogsService = readLogsService;
    }

//    possible query params: page, limit, date, service
    @PreAuthorize("hasAuthority('logs.read')")
    @GetMapping
    public List<LogsReturn> getLogs() {
        log.info("Querying logs from s3 bucket");
        log.info("Querying all");
        List<LogsReturn> queriedLogs = readLogsService.queryAllLogs();
        log.info("Successfully queried s3 bucket");
        return queriedLogs;
    }

    @PreAuthorize("hasAuthority('logs.read')")
    @GetMapping(params = {"offset", "limit"})
    public List<LogsReturn> getLogsWithParams(
            @RequestParam int offset,
            @RequestParam int limit
    ) {
        log.info("Querying logs from s3 bucket with params");
        List<LogsReturn> queriedLogs = readLogsService.queryLogsWithParams(offset, limit);
        log.info("Successfully queried s3 bucket");
        return queriedLogs;
    }

}
