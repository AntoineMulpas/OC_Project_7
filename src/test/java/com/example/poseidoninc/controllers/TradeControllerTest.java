package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.domain.Trade;
import com.example.poseidoninc.services.BidListService;
import com.example.poseidoninc.services.TradeService;
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
@WebMvcTest(controllers = TradeController.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @MockBean
    UserAuthenticationService userAuthenticationService;

    @Test
    @WithMockUser
    void home() throws Exception {
        when(tradeService.getAllTrades()).thenReturn(List.of(new Trade(), new Trade()));
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> list = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("trades");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser
    void addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate() {
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(tradeService.findTradeById(1)).thenReturn(new Trade());
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTrade() {
    }

    @Test
    @WithMockUser
    void deleteTrade() throws Exception {
        when(tradeService.deleteTradeById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}