package com.project.adminproxy.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private boolean create;
    private boolean read;
    private boolean update;
    private boolean delete;

    @Override
    public String toString() {
        return "Create: " + create +
                ", Read: " + read +
                ", Update: " + update +
                ", Delete: " + delete;
    }

}