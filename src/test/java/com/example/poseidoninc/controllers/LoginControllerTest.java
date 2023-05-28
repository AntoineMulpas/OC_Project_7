package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.services.BidListService;
import com.example.poseidoninc.services.UserAuthenticationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    UserAuthenticationService userAuthenticationService;


    @Test
    @WithMockUser
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/login"))
                .andExpect(status().isOk());
    }

    /*
    @Test
    @WithMockUser
    void getListOfUsers() throws Exception {
        when(userAuthenticationService.findAllUsers()).thenReturn(List.of(new User(), new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/app/user/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Disabled
    void addNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate() {
    }



    @Test
    void error() {
    }

     */
}