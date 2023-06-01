package com.havrylenko.library.model.security;

import com.havrylenko.library.model.entity.PersonDetails;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "library_users")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    protected String userId;
    @Setter
    protected String username;
    @Setter
    protected String password;
    @Getter
    protected LocalDateTime accountCreated;
    @Getter
    protected LocalDateTime lastLoginTime;
    @Getter
    protected LocalDateTime lastPasswordChangedDate;
    @Setter
    protected boolean isLocked;
    @ManyToMany(fetch = FetchType.EAGER)
    protected Set<Role> userRoles;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personDetailsId", referencedColumnName = "id")
    private PersonDetails personDetails;

    public User() {
        this.accountCreated = LocalDateTime.now();
        this.lastPasswordChangedDate = LocalDateTime.now();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountCreated = LocalDateTime.now();
        this.lastPasswordChangedDate = LocalDateTime.now();
    }

    public User(String username, String password, PersonDetails personDetails) {
        this(username, password);
        this.personDetails = personDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userRoles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        Period period = Period.between(this.lastLoginTime.toLocalDate(), LocalDate.now());
        return period.getDays() < 365;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        Period period = Period.between(this.lastPasswordChangedDate.toLocalDate(), LocalDate.now());
        return period.getDays() < 90;
    }

    @Override
    public boolean isEnabled() {
        return !this.isLocked;
    }
}
