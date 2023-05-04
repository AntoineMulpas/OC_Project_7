package com.example.poseidoninc.security;

import com.example.poseidoninc.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {

    private final UserAuthenticationService authenticationService;
    private final CustomPasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public AppSecurityConfig(UserAuthenticationService authenticationService, CustomPasswordEncoder passwordEncoder, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authenticationService);
        authenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityContextRepository repository = new HttpSessionSecurityContextRepository();
        http.authenticationProvider(daoAuthenticationProvider());
        http.securityContext(context -> {
            context.securityContextRepository(repository);
        });
        return http
                .csrf()
                .and()
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/style.css").permitAll();
                    authorize.requestMatchers("/bootstrap.min.css").permitAll();
                    authorize.requestMatchers("/app/login").permitAll();
                    authorize.requestMatchers("/user/**").hasAuthority("ADMIN");
                    authorize.requestMatchers("/admin/**").hasAuthority("ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/app/login");
                    form.loginProcessingUrl("/login");
                    form.successHandler(customAuthenticationSuccessHandler);
                })
                .logout(logout -> {
                    logout.logoutSuccessUrl("/app/login");
                })
                .build();
    }



}
