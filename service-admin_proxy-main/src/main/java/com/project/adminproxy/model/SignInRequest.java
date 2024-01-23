package com.project.adminproxy.model;

import com.project.adminproxy.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "SignInRequest{" +
                "email='" + Utils.maskEmail(email) + '\'' +
                '}';
    }
}