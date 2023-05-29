package com.example.poseidoninc.integration;

import com.example.poseidoninc.controllers.UserController;
import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.domain.UserDTO;
import com.example.poseidoninc.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void home() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(result ->
                {
                    List < ? > list = (List <?>) result.getModelAndView().getModel().get("users");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void addNewUser() throws Exception {
        UserDTO user = new UserDTO("antoine", "Password12@", "antoine", "ADMIN");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .with(csrf())
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void showUpdateForm() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1")).andExpect(status().is3xxRedirection());
    }

}
