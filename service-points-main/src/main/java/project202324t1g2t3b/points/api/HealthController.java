package project202324t1g2t3b.points.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Slf4j
public class HealthController {
    @GetMapping
    public ResponseEntity<String> health() {
        log.info("Health endpoint successfully reached");
        return ResponseEntity.ok("Health check successful!");
    }

}
