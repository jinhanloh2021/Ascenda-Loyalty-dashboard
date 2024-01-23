package project202324t1g2t3b.points.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project202324t1g2t3b.points.api.models.Points;
import project202324t1g2t3b.points.biz.PointsService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/points")
public class PointsController {
    private final PointsService pointsService;
    @Autowired
    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @PostMapping()
    public ResponseEntity<?> createPoints(@RequestBody Points points) {
        log.info("Creating new points item");
        Points newPoints = pointsService.createPoints(points);
        log.info("New points item created successfully");
        return ResponseEntity.ok(newPoints);
    }

    @GetMapping()
    public ResponseEntity<List<Points>> getAllPoints() {
        log.info("Getting all Points items");
        List<Points> retrievedPoints = pointsService.getAllPoints();
        log.info("All Points items successfully retrieved");
        return ResponseEntity.ok(retrievedPoints);
    }

    @GetMapping("/{pointsId}")
    public ResponseEntity<Points> getPoints(@PathVariable String pointsId) {
        log.info("Retrieving Points item with id = " + pointsId);
        Points retrievedPoints = pointsService.getPoints(pointsId);
        log.info("Points item with id = " + pointsId + " retrieved successfully");
        return ResponseEntity.ok(retrievedPoints);
    }

    @PutMapping("/{pointsId}")
    public ResponseEntity<Points> updatePoints(@PathVariable String pointsId, @RequestBody Points newPoints) {
        log.info("Updating Points item with id = " + pointsId);
        Points updatedPoints = pointsService.updatePoints(pointsId, newPoints);
        log.info("Successfully updated Points item with id = " + pointsId);
        return ResponseEntity.ok(updatedPoints);
    }

    @DeleteMapping("/{pointsId}")
    public ResponseEntity<Points> deletePoints(@PathVariable String pointsId) {
        log.info("Deleting Points item with id = " + pointsId);
        Points deletedPoints = pointsService.deletePoints(pointsId);
        log.info("Successfully deleted Points item with id = " + pointsId);
        return ResponseEntity.ok(deletedPoints);
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<List<Points>> getAllUsersPoints(@PathVariable String userid) {
        log.info("Getting all Points items belonging to user (id: " + userid + ")");
        List<Points> retrievedPoints = pointsService.getAllUsersPoints(userid);
        log.info("All Points items successfully retrieved");
        return ResponseEntity.ok(retrievedPoints);
    }

    @GetMapping("/users/{userid}/{appName}")
    public ResponseEntity<List<Points>> getUsersPointsByAppName(@PathVariable String userid, @PathVariable String appName) {
        log.info("Getting all Points items belonging to user (id: " + userid + ") with (appName: " + appName + ")");
        List<Points> retrievedPoints = pointsService.getUsersPointsByAppName(userid, appName);
        log.info("All Points items successfully retrieved");
        return ResponseEntity.ok(retrievedPoints);
    }

}
