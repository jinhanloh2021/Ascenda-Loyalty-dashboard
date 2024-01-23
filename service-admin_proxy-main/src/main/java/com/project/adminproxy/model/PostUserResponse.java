package com.project.adminproxy.model;

import com.project.adminproxy.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserResponse {
    private String name;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + Utils.maskEmail(email) + '\'' +
                '}';
    }
}
