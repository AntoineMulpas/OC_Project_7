package com.example.poseidoninc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * THIS CLASS IS AN IMPLEMENTATION OF UserAuthentication AND ALLOWS ACCESS
 * TO THE SECURITY CONTEXT HOLDER TO GET INFORMAITON ON LOGGED USER
 */

public class UserAuthenticationImpl implements UserAuthentication{

    /**
     * THIS METHOD OVERRIDE AUTHENTICATION AND ACCESS THE SECURITY CONTEXT HOLDER TO GET
     * INFORMATION ON LOGGED USER
     * @return
     */

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
