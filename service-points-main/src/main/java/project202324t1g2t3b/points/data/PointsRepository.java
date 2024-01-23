package project202324t1g2t3b.points.data;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import project202324t1g2t3b.points.api.models.Points;
import project202324t1g2t3b.points.biz.models.PointsBiz;
import project202324t1g2t3b.points.data.models.PointsDoc;

import javax.validation.Valid;

@Repository
@Slf4j
public class PointsRepository {

    private final ModelMapper modelMapper;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public PointsRepository(AmazonDynamoDB amazonDynamoDB, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public PointsBiz createPoints(@Valid PointsBiz pointsBiz) {
        PointsDoc pointsDoc = modelMapper.map(pointsBiz, PointsDoc.class);
        dynamoDBMapper.save(pointsDoc);
        log.info(pointsDoc.toString() + " saved to db");
        return pointsBiz;
    }

    public PointsBiz getPoints(String pointsId) {
        return modelMapper.map(dynamoDBMapper.load(PointsDoc.class, pointsId), PointsBiz.class);
    }

    public List<PointsBiz> getAllPoints() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        return dynamoDBMapper.scan(PointsDoc.class, scanExpression)
                .stream()
                .map(pointsDoc -> modelMapper.map(pointsDoc, PointsBiz.class))
                .collect(Collectors.toList());
    }

    public PointsBiz updatePoints(String pointsId, @Valid PointsBiz updatedPointsBiz) {
        PointsDoc updatedPointsDoc = modelMapper.map(updatedPointsBiz, PointsDoc.class);
        updatedPointsDoc.setPointsId(pointsId);
        dynamoDBMapper.save(updatedPointsDoc);
        return updatedPointsBiz;
    }

    public PointsBiz deletePoints(String pointsId) {
        PointsDoc pointsToDelete = dynamoDBMapper.load(PointsDoc.class, pointsId);
        dynamoDBMapper.delete(pointsToDelete);
        return modelMapper.map(pointsToDelete, PointsBiz.class);
    }

    public List<PointsBiz> getAllUsersPoints(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<PointsDoc> queryExpression = new DynamoDBQueryExpression<PointsDoc>()
                .withIndexName("UserIdIndex")  // Specify the global secondary index name
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :val1")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.query(PointsDoc.class, queryExpression)
                .stream()
                .map(pointsDoc -> modelMapper.map(pointsDoc, PointsBiz.class))
                .collect(Collectors.toList());
    }

    void setDynamoDBMapper(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

}
