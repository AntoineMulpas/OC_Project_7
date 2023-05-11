package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.services.BidListService;
import com.example.poseidoninc.services.UserAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = BidListController.class)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @MockBean
    UserAuthenticationService userAuthenticationService;


    @Test
    @WithMockUser
    void home() throws Exception {
        when(bidListService.findAllBids()).thenReturn(List.of(new Bid(), new Bid()));
        mockMvc.perform(MockMvcRequestBuilders.get("/bid/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> bidList = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("bidList");
                    assertEquals(2, bidList.size());
                });
    }

    @Test
    @WithMockUser
    void addBidForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bid/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void validate() throws Exception {
        when(bidListService.saveNewBid(any())).thenReturn(new Bid());
        mockMvc.perform(post("/bid/validate")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Bid(1, "test", "test", 10.1))))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(bidListService.getBidById(1)).thenReturn(new Bid());
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Disabled
    void updateBid() throws Exception {
        Bid bid = new Bid(1, "test", "test", 10.1);
        when(bidListService.updateBid(1, bid)).thenReturn(bid);

        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bid)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void deleteBid() throws Exception {
        when(bidListService.deleteBid(1)).thenReturn(true);
        when(bidListService.findAllBids()).thenReturn(List.of(new Bid(), new Bid()));
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}