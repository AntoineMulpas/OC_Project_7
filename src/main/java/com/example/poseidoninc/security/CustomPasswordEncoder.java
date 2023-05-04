package com.example.poseidoninc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPasswordEncoder {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
