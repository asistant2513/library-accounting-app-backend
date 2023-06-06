package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.model.security.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String userId;
    private String username;
    private LocalDateTime accountCreated;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastPasswordChangedDate;
    private boolean isLocked;
    private Role role;

    public UserDTO(final User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.accountCreated = user.getAccountCreated();
        this.lastLoginTime = user.getLastLoginTime();
        this.lastPasswordChangedDate = user.getLastPasswordChangedDate();
        this.isLocked = !user.isAccountNonLocked();
        this.role = (Role)user.getAuthorities().iterator().next();
    }
}
