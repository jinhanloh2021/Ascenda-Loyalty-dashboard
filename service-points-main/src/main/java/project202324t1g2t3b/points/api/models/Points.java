package project202324t1g2t3b.points.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Points {

    private String pointsId;

    private long balance;

    // TODO: check if required
    @NotNull(message = "appName is a required field")
    private String appName;

    @NotNull(message = "Points must belong to a User")
    private String userId;


    @Override
    public String toString() {
        return "Points{" +
                "pointsId=" + pointsId +
                ", balance=" + balance +
                ", appName='" + appName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
