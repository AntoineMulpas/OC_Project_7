package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.domain.Rating;
import com.example.poseidoninc.services.BidListService;
import com.example.poseidoninc.services.RatingService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    UserAuthenticationService userAuthenticationService;


    @Test
    @WithMockUser
    void home() throws Exception {
        when(ratingService.getAllRating()).thenReturn(List.of(new Rating(), new Rating()));
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> list = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("ratings");
                    assertEquals(2, list.size());
                });
    }

    @Test
    @WithMockUser
    void addRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void validate() throws Exception {
        Rating rating = new Rating(
                1,
                "test",
                "test",
                "test",
                12
        );
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .flashAttr("rating", rating))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void validateShouldNotWorkIsOrderNumberIsNull() throws Exception {
        Rating rating = new Rating(
                1,
                "test",
                "test",
                "test",
                null
        );
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .flashAttr("rating", rating))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(ratingService.getRatingById(1)).thenReturn(new Rating());
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateRating() throws Exception {
        Rating rating = new Rating(
                1,
                "test",
                "test",
                "test",
                12
        );
        mockMvc.perform(post("/rating/update/1")
                        .with(csrf())
                        .flashAttr("ratings", rating))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void updateRatingShouldNotWorkIfOrderNumberIsNull() throws Exception {
        Rating rating = new Rating(
                1,
                "test",
                "test",
                "test",
                null
        );
        mockMvc.perform(post("/rating/update/1")
                        .with(csrf())
                        .flashAttr("ratings", rating))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteRating() throws Exception {
        when(ratingService.deleteRatingById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}