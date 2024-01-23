package project202324t1g2t3b.points.biz.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointsBiz {
    private String pointsId;

    private long balance;

    private String appName;

    private String userId;

    @Override
    public String toString() {
        return "PointsBiz{" +
                "pointsId=" + pointsId +
                ", balance=" + balance +
                ", appName='" + appName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
