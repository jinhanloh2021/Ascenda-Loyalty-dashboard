package com.project.adminproxy.controller;

import com.project.adminproxy.model.Role;
import com.project.adminproxy.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/v1/app/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(HttpServletRequest servletRequest) {
        log.info("Retrieving all roles");
        List<Role> allRoles = roleService.getAllRoles(servletRequest);
        log.info("All Roles successfully received");
        return ResponseEntity.ok(allRoles);
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRole(@PathVariable UUID roleId, HttpServletRequest servletRequest) {
        log.info("Retrieving Role with id = " + roleId);
        Role role = roleService.getRoleById(roleId, servletRequest);
        log.info("Successfully retrieved Role with id = " + role.getRoleId());
        return ResponseEntity.ok(role);
    }

    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role, HttpServletRequest servletRequest) {
        log.info("Creating new Role");
        Role createdRole = roleService.createRole(role, servletRequest);
        log.info("Successfully created new Role with id = " + createdRole.getRoleId());
        return ResponseEntity.ok(createdRole);
    }

    @PreAuthorize("hasAuthority('role.update')")
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable UUID roleId, @RequestBody Role role,
            HttpServletRequest servletRequest) {
        log.info("Updating Role with id = " + roleId);
        Role updatedRole = roleService.updateRole(roleId, role, servletRequest);
        log.info("Successfully updated Role with id = " + roleId);
        return ResponseEntity.ok(updatedRole);
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable UUID roleId, HttpServletRequest servletRequest) {
        log.info("Deleting Role with id = " + roleId);

        boolean isDeleted = roleService.deleteRole(roleId, servletRequest);

        if (!isDeleted) {
            log.info("Role with id = " + roleId + " not found.");
            return ResponseEntity.notFound().build();
        }

        log.info("Successfully deleted Role with id = " + roleId);
        return ResponseEntity.ok("Successfully deleted Role with id = " + roleId);

    }
}
