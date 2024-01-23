package com.project.adminproxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakerRequestRequestData {
    private String checker_user;
    private String maker_user;
    private String request_data;
    private String action_type;
    private String checker_email;
    private String maker_role;
    private String checker_role;
}
