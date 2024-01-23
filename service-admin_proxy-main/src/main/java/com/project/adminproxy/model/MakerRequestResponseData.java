package com.project.adminproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakerRequestResponseData {
    @JsonProperty("RequestId")
    private String RequestId;

    @JsonProperty("Checker")
    private String Checker;

    @JsonProperty("Maker")
    private String Maker;

    @JsonProperty("RequestData")
    private String RequestData;

    @JsonProperty("RequestType")
    private String RequestType;

    @JsonProperty("Status")
    private String Status;
}
