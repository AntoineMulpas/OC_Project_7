package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.services.BidListService;
import com.example.poseidoninc.services.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserAuthenticationService userAuthenticationService;


    @Test
    @WithMockUser
    void home() throws Exception {
        when(userAuthenticationService.findAllUsers()).thenReturn(List.of(new User(), new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(result ->
                {
                    List < ? > list = (List <?>) result.getModelAndView().getModel().get("users");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser
    void addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void validate() {
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(userAuthenticationService.findUserById(1)).thenReturn(new User());
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateUser() {
    }

    @Test
    @WithMockUser
    void deleteUser() throws Exception {
        when(userAuthenticationService.deleteUserById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1")).andExpect(status().is3xxRedirection());
    }
}