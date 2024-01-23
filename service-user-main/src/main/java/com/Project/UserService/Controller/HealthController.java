package com.Project.UserService.Controller;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/health")
@Slf4j
public class HealthController {

    @GetMapping()
    public String healthCheck() {
        log.info("Health check successful, application is healthy.");
        return "Application is healthy";
    }
}
