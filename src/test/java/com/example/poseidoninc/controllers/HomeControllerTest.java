package com.example.poseidoninc.controllers;

import com.example.poseidoninc.services.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {


    @Autowired
    private MockMvc mockMvc;




    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void homeShouldWorkForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void homeShouldWorkForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void adminHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void adminHomeShouldNotWorkIfAuthorityEqualsUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/home"))
                .andExpect(status().is4xxClientError());
    }
}