package com.example.poseidoninc.integration;

import com.example.poseidoninc.domain.Trade;
import com.example.poseidoninc.repositories.TradeRepository;
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
public class TradeIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    @WithMockUser
    void home() throws Exception {
        when(tradeRepository.findAll()).thenReturn(List.of(new Trade(), new Trade()));
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> list = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("trades");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(new Trade()));
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteTrade() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection());
    }

}
