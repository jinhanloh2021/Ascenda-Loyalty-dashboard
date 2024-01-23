package com.project.adminproxy.model;

import com.project.adminproxy.utils.Utils;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserRequest {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "email is required")
    @Email
    private String email;

    private String roleName;

    @Override
    public String toString() {
        return "User{" +
                " name='" + name + '\'' +
                ", email='" + Utils.maskEmail(email) + '\'' +
                '}';
    }

}