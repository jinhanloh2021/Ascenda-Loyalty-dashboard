package com.Project.UserService.Model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@DynamoDBTable(tableName = "User")
public class User {

    @DynamoDBHashKey(attributeName = "userId")
    private String userId;

    @NotNull(message = "Name is required")
    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @NotNull(message = "email is required")
    @Email
    @DynamoDBIndexHashKey(attributeName = "email", globalSecondaryIndexName = "emailIndex")
    private String email;

    @NotNull(message = "password is required")
    @DynamoDBAttribute(attributeName = "password")
    private String password;

    @DynamoDBAttribute(attributeName = "role")
    private String role;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
