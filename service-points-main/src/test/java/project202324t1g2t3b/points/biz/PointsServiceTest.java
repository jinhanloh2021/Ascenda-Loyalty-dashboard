package project202324t1g2t3b.points.biz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;
import project202324t1g2t3b.points.api.models.Points;
import project202324t1g2t3b.points.biz.models.PointsBiz;
import project202324t1g2t3b.points.data.PointsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static project202324t1g2t3b.points.biz.Fakes.*;

@ExtendWith(MockitoExtension.class)
public class PointsServiceTest {
    @InjectMocks
    private PointsService pointsService;

    @Mock
    private PointsRepository pointsRepository;

    @Mock
    private ModelMapper modelMapper;
    @Test
    public void testCreatePoints() {
        when(pointsRepository.createPoints(any(PointsBiz.class))).thenReturn(fakePointsBiz);
        when(modelMapper.map(any(), eq(PointsBiz.class))).thenReturn(fakePointsBiz);

        pointsService.createPoints(fakePoints);

        verify(pointsRepository, times(1)).createPoints(any(PointsBiz.class));
    }

    @Test
    public void testGetAllPoints() {
        List<PointsBiz> pointsBizList = List.of(fakePointsBiz);
        when(pointsRepository.getAllPoints()).thenReturn(pointsBizList);
        when(modelMapper.map(any(), eq(Points.class))).thenReturn(fakePoints);

        List<Points> pointsList = pointsService.getAllPoints();

        verify(pointsRepository, times(1)).getAllPoints();
        assertEquals(1, pointsList.size());
    }

    @Test
    public void testGetPoints() {
        String pointsId = fakePointsId;
        when(pointsRepository.getPoints(pointsId)).thenReturn(fakePointsBiz);
        when(modelMapper.map(any(), eq(Points.class))).thenReturn(fakePoints);

        Points result = pointsService.getPoints(pointsId);

        verify(pointsRepository, times(1)).getPoints(pointsId);
        assertEquals(modelMapper.map(fakePointsBiz, Points.class), result);
    }

    @Test
    public void testUpdatePoints() {
        when(pointsRepository.updatePoints(fakePointsId, fakePointsBiz)).thenReturn(fakePointsBiz);
        when(modelMapper.map(any(), eq(PointsBiz.class))).thenReturn(fakePointsBiz);
        when(modelMapper.map(any(), eq(Points.class))).thenReturn(fakePoints);

        Points result = pointsService.updatePoints(fakePointsId, fakePoints);

        verify(pointsRepository, times(1)).updatePoints(fakePointsId, fakePointsBiz);
        assertEquals(fakePoints, result);
    }

    @Test
    public void testDeletePoints() {
        when(pointsRepository.deletePoints(fakePointsId)).thenReturn(fakePointsBiz);

        Points result = pointsService.deletePoints(fakePointsId);

        verify(pointsRepository, times(1)).deletePoints(fakePointsId);
        assertEquals(modelMapper.map(fakePointsBiz, Points.class), result);
    }

    @Test
    public void testGetAllUsersPoints() {
        List<PointsBiz> pointsBizList = List.of(fakePointsBiz);
        when(pointsRepository.getAllUsersPoints(fakeUserId)).thenReturn(pointsBizList);

        List<Points> pointsList = pointsService.getAllUsersPoints(fakeUserId);

        verify(pointsRepository, times(1)).getAllUsersPoints(fakeUserId);
        assertEquals(1, pointsList.size());
    }

    @Test
    public void testGetUsersPointsByAppName() {
        List<PointsBiz> pointsBizList = List.of(fakePointsBiz);
        when(pointsRepository.getAllUsersPoints(fakeUserId)).thenReturn(pointsBizList);

        List<Points> pointsList = pointsService.getUsersPointsByAppName(fakeUserId, fakeAppName);

        verify(pointsRepository, times(1)).getAllUsersPoints(fakeUserId);
        assertEquals(1, pointsList.size());
    }
}


final class Fakes {
    static final String fakeUserId = "fakeUserId";

    static final String fakePointsId = "fakePointsId";

    static final String fakeAppName = "fake-app-name";

    static final Points fakePoints = new Points(fakePointsId, 1L, fakeAppName, fakeUserId);

    static final PointsBiz fakePointsBiz = new PointsBiz(fakePointsId, 1L, fakeAppName, fakeUserId);
    
}
