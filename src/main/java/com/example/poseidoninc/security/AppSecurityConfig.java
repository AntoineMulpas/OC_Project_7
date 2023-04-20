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
    private final CustomPassordEncoder      passwordEncoder;

    @Autowired
    public AppSecurityConfig(UserAuthenticationService authenticationService, CustomPassordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
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
                .csrf().disable()
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/resources/css/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/app/login").permitAll();
                    form.loginProcessingUrl("/login");
                    form.defaultSuccessUrl("/bidList/list");
                })
                .logout(logout -> {
                    logout.logoutUrl("/app-logout");
                    logout.logoutSuccessUrl("/app/login");
                })
                .build();
    }



}
