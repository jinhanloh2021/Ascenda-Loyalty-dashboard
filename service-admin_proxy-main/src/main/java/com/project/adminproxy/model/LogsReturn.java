package com.project.adminproxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogsReturn {
    private String logsId;
    private String dateTime;
    private String device;
    private String description;

    @Override
    public String toString() {
        return "logsId:" + logsId + ",dateTime:" + dateTime + ",device:" + device + ",description:" + description;
    }
}
