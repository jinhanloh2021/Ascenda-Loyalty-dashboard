package project202324t1g2t3b.points.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project202324t1g2t3b.points.biz.models.PointsBiz;
import project202324t1g2t3b.points.data.models.PointsDoc;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static project202324t1g2t3b.points.data.Fakes.*;

@ExtendWith(MockitoExtension.class)
public class PointsRepositoryTest {

    @Mock
    private AmazonDynamoDB amazonDynamoDB;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PointsRepository pointsRepository;

    @Test
    public void testCreatePoints() {
        // Arrange

        when(modelMapper.map(eq(fakePointsBiz), eq(PointsDoc.class))).thenReturn(fakePointsDoc);
        DynamoDBMapper mockDynamoDBMapper = mock(DynamoDBMapper.class);
        doNothing().when(mockDynamoDBMapper).save(any(PointsDoc.class));
        pointsRepository.setDynamoDBMapper(mockDynamoDBMapper);

        // Act
        PointsBiz result = pointsRepository.createPoints(fakePointsBiz);

        // Assert
        verify(mockDynamoDBMapper, times(1)).save(any(PointsDoc.class));
        assertEquals(fakePointsBiz, result);
    }

    @Test
    public void testGetPoints() {
        // Arrange
        DynamoDBMapper mockDynamoDBMapper = mock(DynamoDBMapper.class);
        when(mockDynamoDBMapper.load(eq(PointsDoc.class), eq(fakePointsId))).thenReturn(fakePointsDoc);
        pointsRepository.setDynamoDBMapper(mockDynamoDBMapper);
        when(modelMapper.map(eq(fakePointsDoc), eq(PointsBiz.class))).thenReturn(fakePointsBiz);

        // Act
        PointsBiz result = pointsRepository.getPoints(fakePointsId);

        // Assert
        assertEquals(fakePointsBiz, result);
    }

//    @Test
//    public void testGetAllPoints() {
//        // Arrange
//        DynamoDBMapper mockDynamoDBMapper = mock(DynamoDBMapper.class);
//        when(mockDynamoDBMapper.scan(eq(PointsDoc.class), any(DynamoDBScanExpression.class))).thenReturn((PaginatedScanList<PointsDoc>) Stream.of(fakePointsDoc));
//        pointsRepository.setDynamoDBMapper(mockDynamoDBMapper);
//        when(modelMapper.map(eq(fakePointsDoc), eq(PointsBiz.class))).thenReturn(fakePointsBiz);
//
//        // Act
//        List<PointsBiz> result = pointsRepository.getAllPoints();
//
//        // Assert
//        assertEquals(fakePointsBiz, result.get(0));
//        assertEquals(1, result.size());
//    }

}

final class Fakes {
    static final String fakeUserId = "fakeUserId";

    static final String fakePointsId = "fakePointsId";

    static final String fakeAppName = "fake-app-name";

    static final PointsBiz fakePointsBiz = new PointsBiz(fakePointsId, 1L, fakeAppName, fakeUserId);

    static final PointsDoc fakePointsDoc = new PointsDoc(fakePointsId, 1L, fakeAppName, fakeUserId);
}
