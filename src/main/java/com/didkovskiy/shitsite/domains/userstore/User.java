package com.didkovskiy.shitsite.domains.userstore;

import com.didkovskiy.shitsite.domains.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class User implements UserDetails {
    private static final long serialVersionUID = 6804865340415561595L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @OneToMany
    private List<Message> messages;

    public User(String username, String password, String email, Role role, Status status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return Objects.equals(this.username, ((User) obj).username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.requireNonNull(this.username).hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + " [" +
                "Username=" + this.username + ", " +
                "Password=[PROTECTED], " +
                "Email=" + this.email + ", " +
                "Enabled=" + this.isEnabled() + ", " +
                "AccountNonExpired=" + this.isAccountNonExpired() + ", " +
                "credentialsNonExpired=" + this.isCredentialsNonExpired() + ", " +
                "AccountNonLocked=" + this.isAccountNonLocked() + ", " +
                "Granted Authorities=" + this.getAuthorities() + "]";
    }

    public boolean isAdmin(){
        return role.equals(Role.ADMIN);
    }

}
