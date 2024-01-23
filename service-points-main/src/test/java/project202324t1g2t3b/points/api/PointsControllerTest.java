package project202324t1g2t3b.points.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project202324t1g2t3b.points.api.models.Points;
import project202324t1g2t3b.points.biz.PointsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static project202324t1g2t3b.points.api.Fakes.*;

@ExtendWith(MockitoExtension.class)
class PointsControllerTest {

    @InjectMocks
    private PointsController pointsController;

    @Mock
    private PointsService pointsService;

    @Test
    void testCreatePoints() {
        // Arrange
        Points points = fakePoints;

        // Act
        ResponseEntity<?> response = pointsController.createPoints(points);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(pointsService, times(1)).createPoints(eq(points));
    }

    @Test
    void testGetAllPoints() {
        // Arrange
        List<Points> mockPointsList = List.of(fakePoints);
        when(pointsService.getAllPoints()).thenReturn(mockPointsList);

        // Act
        ResponseEntity<List<Points>> response = pointsController.getAllPoints();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPointsList, response.getBody());
        verify(pointsService, times(1)).getAllPoints();
    }

    @Test
    void testGetPoints() {
        // Arrange
        String pointsId = fakePointsId;
        Points mockPoints = fakePoints;
        when(pointsService.getPoints(pointsId)).thenReturn(mockPoints);

        // Act
        ResponseEntity<Points> response = pointsController.getPoints(pointsId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPoints, response.getBody());
        verify(pointsService, times(1)).getPoints(eq(pointsId));
    }

    @Test
    void testUpdatePoints() {
        // Arrange
        String pointsId = fakePointsId;
        Points newPoints = fakePoints;
        Points mockUpdatedPoints = fakePoints;
        when(pointsService.updatePoints(eq(pointsId), any(Points.class))).thenReturn(mockUpdatedPoints);

        // Act
        ResponseEntity<Points> response = pointsController.updatePoints(pointsId, newPoints);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUpdatedPoints, response.getBody());
        verify(pointsService, times(1)).updatePoints(eq(pointsId), eq(newPoints));
    }

    @Test
    void testDeletePoints() {
        // Arrange
        String pointsId = fakePointsId;
        Points mockDeletedPoints = fakePoints;
        when(pointsService.deletePoints(pointsId)).thenReturn(mockDeletedPoints);

        // Act
        ResponseEntity<Points> response = pointsController.deletePoints(pointsId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDeletedPoints, response.getBody());
        verify(pointsService, times(1)).deletePoints(eq(pointsId));
    }

    @Test
    void testGetAllUsersPoints() {
        // Arrange
        String userId = fakeUserId;
        List<Points> mockPointsList = List.of(fakePoints);
        when(pointsService.getAllUsersPoints(userId)).thenReturn(mockPointsList);

        // Act
        ResponseEntity<List<Points>> response = pointsController.getAllUsersPoints(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPointsList, response.getBody());
        verify(pointsService, times(1)).getAllUsersPoints(userId);
    }

    @Test
    void testGetUsersPointsByAppName() {
        // Arrange
        String userId = fakeUserId;
        String appName = fakeAppName;
        List<Points> mockPointsList = List.of(fakePoints);
        when(pointsService.getUsersPointsByAppName(userId, appName)).thenReturn(mockPointsList);

        // Act
        ResponseEntity<List<Points>> response = pointsController.getUsersPointsByAppName(userId, appName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPointsList, response.getBody());
        verify(pointsService, times(1)).getUsersPointsByAppName(userId, appName);
    }
}


final class Fakes {
    static final String fakePointsId = "fakePointsId";

    static final String fakeUserId = "fakeUserId";

    static final String fakeAppName = "fake-app-name";

    static final Points fakePoints = new Points();
}

