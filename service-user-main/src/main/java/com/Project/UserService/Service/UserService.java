package com.Project.UserService.Service;

import com.Project.UserService.Exception.UserAlreadyExistsException;
import com.Project.UserService.Exception.UserNotFoundException;
import com.Project.UserService.Model.User;
import java.util.*;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
public class UserService {

    private final AmazonDynamoDB amazonDynamoDB;
    private final ModelMapper modelMapper;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UserService(AmazonDynamoDB amazonDynamoDB, ModelMapper modelMapper) {
        this.amazonDynamoDB = amazonDynamoDB;
        this.modelMapper = modelMapper;
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public User createUser(@Valid User user) {
        //check if userId exists
        String userId = user.getUserId();
        User retrievedUser = dynamoDBMapper.load(User.class, userId);
        if (retrievedUser != null) {
            throw new UserAlreadyExistsException("User with userId: " + userId + "already exists");
        }

        //check if email exists
        String email = user.getEmail();
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(email));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName("emailIndex")  // Specify the global secondary index name
                .withConsistentRead(false)
                .withKeyConditionExpression("email = :val1")
                .withExpressionAttributeValues(eav);

        PaginatedQueryList<User> queryList = dynamoDBMapper.query(User.class, queryExpression);

        // Assuming you want to return the first item if it exists
        User retrievedUser2 = queryList.isEmpty() ? null : queryList.get(0);
        if (retrievedUser2 != null) {
            throw new UserAlreadyExistsException("User with email: " + email + "already exists");
        }

        dynamoDBMapper.save(user);
        return user;
    }

    public User getUser(String userId) {
        User user = dynamoDBMapper.load(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public User getUserByEmail(String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(email));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName("emailIndex")  // Specify the global secondary index name
                .withConsistentRead(false)
                .withKeyConditionExpression("email = :val1")
                .withExpressionAttributeValues(eav);

        PaginatedQueryList<User> queryList = dynamoDBMapper.query(User.class, queryExpression);

        // Assuming you want to return the first item if it exists
        User user = queryList.isEmpty() ? null : queryList.get(0);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public List<User> getAllUsers() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(User.class, scanExpression);
    }

    public User updateUser(String userId, @Valid User updatedUser) {
        getUser(userId);
        updatedUser.setUserId(userId);
        dynamoDBMapper.save(updatedUser, new DynamoDBSaveExpression().withExpectedEntry("userId",
                new ExpectedAttributeValue(new AttributeValue().withS(userId))));
        return updatedUser;
    }

    public User deleteUser(String userId) {
        getUser(userId);
        User userToDelete = dynamoDBMapper.load(User.class, userId);
        dynamoDBMapper.delete(userToDelete);
        return userToDelete;
    }

}
