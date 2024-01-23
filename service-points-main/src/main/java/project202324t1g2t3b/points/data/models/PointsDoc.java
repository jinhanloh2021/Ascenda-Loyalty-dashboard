package project202324t1g2t3b.points.data.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import jakarta.websocket.OnOpen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Points")
public class PointsDoc {

    @DynamoDBHashKey(attributeName = "pointsId")
    private String pointsId;

    @DynamoDBAttribute(attributeName = "balance")
    private long balance;

    @DynamoDBAttribute(attributeName = "appName")
    private String appName;

    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "UserIdIndex")
    @NotNull(message = "Points must belong to a User")
    private String userId;


    @Override
    public String toString() {
        return "PointsDoc{" +
                "pointsId=" + pointsId +
                ", balance=" + balance +
                ", appName='" + appName + '\'' +
                ", userId=" + userId +
                '}';
    }

}
