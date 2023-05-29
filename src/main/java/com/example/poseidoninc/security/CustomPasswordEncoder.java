package com.example.poseidoninc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * THIS CLASS IS RESPONSIBLE FOR CONFIGURING A CUSTOM PASSWORD ENCODER TO HASH THE PASSWORD
 */

@Configuration
public class CustomPasswordEncoder {

    /**
     * THIS METHOD CALLED THE encode() METHOD ON passwordEncoder() TO HASH A PASSWORD
     * @param rawPassword
     * @return HASHED PASSWORD AS A STRING
     */

    public String encodePassword(String rawPassword) {
        return passwordEncoder().encode(rawPassword);
    }

    /**
     * THIS BEAN INITIALIZES AN INSTANCE OF BCryptPasswordEncoder TO HASH THE PASSWORD
     * @return
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
