package com.project.adminproxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @JsonProperty("RoleID")
    private UUID roleId;

    @NotNull(message = "Name is required")
    @JsonProperty("RoleName")
    private String roleName;

    @JsonProperty("UserStorage")
    private Permission userStoragePermission;
    @JsonProperty("PointLedger")
    private Permission pointLedgerPermission;
    @JsonProperty("Logs")
    private Permission logsPermission;
    @JsonProperty("Role")
    private Permission rolePermission;

    @Override
    public String toString() {
        String toReturn = roleName + " has the following permissions: \n" + "userStoragePermissions: "
                + userStoragePermission.toString() + "\n" +
                "pointLedgerPermission: " + pointLedgerPermission.toString() + "\n" +
                "logsPermission: " + logsPermission.toString() + "\n" +
                "rolePermission: " + rolePermission.toString() + "\n";
        return toReturn;
    }

    public List<String> getRoleAuthorities() {
        List<String> authorities = new ArrayList<>();

        if (userStoragePermission.isCreate()) {
            authorities.add("user.create");
        }
        if (userStoragePermission.isRead()) {
            authorities.add("user.read");
        }
        if (userStoragePermission.isUpdate()) {
            authorities.add("user.update");
        }
        if (userStoragePermission.isDelete()) {
            authorities.add("user.delete");
        }
        if (pointLedgerPermission.isCreate()) {
            authorities.add("points.create");
        }
        if (pointLedgerPermission.isRead()) {
            authorities.add("points.read");
        }
        if (pointLedgerPermission.isUpdate()) {
            authorities.add("points.update");
        }
        if (pointLedgerPermission.isDelete()) {
            authorities.add("points.delete");
        }
        if (logsPermission.isCreate()) {
            authorities.add("logs.create");
        }
        if (logsPermission.isRead()) {
            authorities.add("logs.read");
        }
        if (logsPermission.isUpdate()) {
            authorities.add("logs.update");
        }
        if (logsPermission.isDelete()) {
            authorities.add("logs.delete");
        }
        if (rolePermission.isCreate()) {
            authorities.add("role.create");
        }
        if (rolePermission.isRead()) {
            authorities.add("role.read");
        }
        if (rolePermission.isUpdate()) {
            authorities.add("role.update");
        }
        if (rolePermission.isDelete()) {
            authorities.add("role.delete");
        }

        return authorities;
    }
}