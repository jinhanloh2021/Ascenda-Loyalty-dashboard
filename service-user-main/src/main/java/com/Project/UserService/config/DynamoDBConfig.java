package com.Project.UserService.config;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("classpath:config.properties")
public class DynamoDBConfig {
    @Value("${aws.accessKeyId}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    private final String tableName = "User";

//    @Bean
//    public void seeEnv() {
//        System.out.println("here" + awsAccessKey);
//        System.out.println(awsSecretKey);
//    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonDynamoDBClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTHEAST_1).build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public void createTable() {
        final AmazonDynamoDB ddb = amazonDynamoDB();

        try {
            // Check if the table exists
            DescribeTableRequest describeTableRequest = new DescribeTableRequest(tableName);
            TableDescription tableDescription = ddb.describeTable(describeTableRequest).getTable();

            // If the table doesn't exist, create it
            if (tableDescription == null) {
                CreateTableRequest request = new CreateTableRequest()
                        .withAttributeDefinitions(
                                new AttributeDefinition("userId", ScalarAttributeType.N),
                                new AttributeDefinition("role", ScalarAttributeType.S))
                        .withKeySchema(
                                new KeySchemaElement("userId", KeyType.HASH),
                                new KeySchemaElement("role", KeyType.RANGE))
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                        .withTableName(tableName);

                ddb.createTable(request);
            } else {
                // Table already exists, print a message or perform other necessary actions
                System.out.println("Table already exists: " + tableName);
            }
        } catch (ResourceNotFoundException e) {
            // Handle the exception appropriately
            System.err.println("Table not found: " + e.getErrorMessage());
        } catch (AmazonServiceException e) {
            // Handle the exception appropriately
            System.err.println("Error creating table: " + e.getErrorMessage());
        }
    }
}
