package com.example.poseidoninc.integration;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.repositories.BidListRepository;
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
public class BidListIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListRepository bidListRepository;


    @Test
    @WithMockUser
    void HomePage() throws Exception {
        when(bidListRepository.findAll()).thenReturn(List.of(new Bid(), new Bid()));
        mockMvc.perform(MockMvcRequestBuilders.get("/bid/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> bidList = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("bidList");
                    assertEquals(2, bidList.size());
                });
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(new Bid()));
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteBid() throws Exception {
        when(bidListRepository.findAll()).thenReturn(List.of(new Bid(), new Bid()));
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}
