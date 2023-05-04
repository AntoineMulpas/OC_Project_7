package com.example.poseidoninc;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoseidonIncApplication implements CommandLineRunner {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public PoseidonIncApplication(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PoseidonIncApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //userAuthenticationService.saveAUser(new User("antoine", "password", "antoine", "admin"));
    }
}
