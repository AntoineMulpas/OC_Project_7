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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void validate() throws Exception {
        CurvePoint curvePoint = new CurvePoint(
                1,
                2,
                Timestamp.valueOf(LocalDateTime.now()),
                2.1,
                2.3,
                Timestamp.valueOf(LocalDateTime.now())
        );
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void validateShouldNotWorkIfCurveIdIsNull() throws Exception {
        CurvePoint curvePoint = new CurvePoint(
                1,
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                2.1,
                2.3,
                Timestamp.valueOf(LocalDateTime.now())
        );
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().isOk());
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
    void updateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(
                1,
                1,
                Timestamp.valueOf(LocalDateTime.now()),
                2.1,
                2.3,
                Timestamp.valueOf(LocalDateTime.now())
        );
        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void updateCurvePointIfCurveIdIsNull() throws Exception {
        CurvePoint curvePoint = new CurvePoint(
                1,
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                2.1,
                2.3,
                Timestamp.valueOf(LocalDateTime.now())
        );
        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteCurvePoint() throws Exception {
        when(curvePointService.deleteCurvePointById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}