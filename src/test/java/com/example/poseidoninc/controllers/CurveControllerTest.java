package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.domain.CurvePoint;
import com.example.poseidoninc.services.CurvePointService;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = CurveController.class)
class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    UserAuthenticationService userAuthenticationService;


    @Test
    @WithMockUser
    void home() throws Exception {
        when(curvePointService.getAllCurvePoint()).thenReturn(List.of(new CurvePoint(), new CurvePoint()));
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> list = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("curvePoint");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser
    void addForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void validate() {
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(curvePointService.findById(1)).thenReturn(new CurvePoint());
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateBid() {
    }

    @Test
    @WithMockUser
    void deleteBid() throws Exception {
        when(curvePointService.deleteCurvePointById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}