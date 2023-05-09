package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Rating;
import com.example.poseidoninc.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    private RatingService underTest;
    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    void setUp() {
        underTest = new RatingService(ratingRepository);
    }

    @Test
    void addNewRating() {
        when(ratingRepository.save(any())).thenReturn(new Rating());
        assertNotNull(underTest.addNewRating(any()));
    }

    @Test
    void getAllRating() {
        when(ratingRepository.findAll()).thenReturn(List.of(new Rating(), new Rating()));
        assertEquals(2, underTest.getAllRating().size());
    }

    @Test
    void deleteRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(new Rating()));
        assertTrue(underTest.deleteRatingById(1));
    }

    @Test
    void deleteRatingByIdShouldReturnFalse() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(underTest.deleteRatingById(1));
    }

    @Test
    void getRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(new Rating()));
        assertNotNull(underTest.getRatingById(1));
    }

    @Test
    void getRatingByIdShouldReturnNull() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.getRatingById(1));
    }

    @Test
    void updateRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(new Rating()));
        when(ratingRepository.save(any())).thenReturn(new Rating());
        assertNotNull(underTest.updateRatingById(1, new Rating()));
    }

    @Test
    void updateRatingByIdShouldReturnNull() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.updateRatingById(1, new Rating()));
    }
}