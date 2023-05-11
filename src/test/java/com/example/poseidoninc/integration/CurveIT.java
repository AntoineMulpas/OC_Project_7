package com.example.poseidoninc.integration;

import com.example.poseidoninc.domain.CurvePoint;
import com.example.poseidoninc.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurveIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointRepository curvePointRepository;


    @Test
    @WithMockUser
    void home() throws Exception {
        when(curvePointRepository.findAll()).thenReturn(List.of(new CurvePoint(), new CurvePoint()));
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> list = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("curvePoint");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(new CurvePoint()));
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteBid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection());
    }

}
