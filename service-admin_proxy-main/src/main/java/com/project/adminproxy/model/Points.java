package com.project.adminproxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Points {

    public Points() {
        this.pointsId = UUID.randomUUID().toString();
    }
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
