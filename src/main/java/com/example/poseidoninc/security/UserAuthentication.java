package com.example.poseidoninc.security;

import org.springframework.security.core.Authentication;

/**
 * THIS INTERFACE ALLOWS ACCESS TO THE AUTHENTICATION INTERFACE OF SPRING SECURITY
 */

public interface UserAuthentication {

    Authentication getAuthentication();

}
