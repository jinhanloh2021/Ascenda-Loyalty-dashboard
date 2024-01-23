package com.project.adminproxy.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class S3QueryRequest {
    private int page;
    private int limit;
    private String date; //yyyy-mm-dd
    private String service;
}
