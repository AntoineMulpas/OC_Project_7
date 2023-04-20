package com.example.poseidoninc.security;

import org.springframework.security.core.Authentication;

public interface UserAuthentication {

    Authentication getAuthentication();

}
