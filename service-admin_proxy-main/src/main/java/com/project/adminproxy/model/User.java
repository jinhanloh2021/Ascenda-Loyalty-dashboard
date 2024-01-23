package com.project.adminproxy.model;

import com.project.adminproxy.utils.Utils;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
public class User implements UserDetails {

    private String userId;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "email is required")
    @Email
    private String email;

    private String password; // salted and hashed. See AuthenticationService signUp()

    private String role;

    @Builder.Default
    private List<String> permissions = new ArrayList<>();// derived from role

    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> perms = this.permissions;
        if (perms == null) {
            perms = new ArrayList<>();
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String p : perms) {
            authorities.add(new SimpleGrantedAuthority(p));
        }
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
    }

    @Override
    public String getUsername() {
        // need to have service method to find user by username (email)
        // email currently implemented, but need to implement in User microservice
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + Utils.maskEmail(email) + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
