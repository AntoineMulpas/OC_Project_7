package com.example.poseidoninc.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        List <? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities().stream().toList();
        if (grantedAuthorities.get(0).equals(new SimpleGrantedAuthority("ADMIN"))) {
            response.sendRedirect("/admin/home");
        } else {
            response.sendRedirect("/");
        }
    }

}
