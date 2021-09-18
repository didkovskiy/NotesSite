package com.didkovskiy.shitsite.domains.userstore;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private final String username;
    private String password;
    private String email;

    public User toUsualUser(PasswordEncoder passwordEncoder){
        return new User(username, passwordEncoder.encode(password), email, Role.USER, Status.ACTIVE);
    }
}
