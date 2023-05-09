package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.repositories.BidListRepository;
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
class BidListServiceTest {

    private BidListService underTest;

    @Mock
    private BidListRepository bidListRepository;

    @BeforeEach
    void setUp() {
        underTest = new BidListService(bidListRepository);
    }

    @Test
    void findAllBids() {
        when(bidListRepository.findAll()).thenReturn(List.of(new Bid(), new Bid()));
        assertEquals(2, underTest.findAllBids().size());
    }

    @Test
    void saveNewBid() {
        Bid bid = new Bid(1, "test", "test", 11.3);
        when(bidListRepository.save(any())).thenReturn(bid);
        Bid savedBid = underTest.saveNewBid(bid);
        assertEquals(bid.getAccount(), savedBid.getAccount());
    }

    @Test
    void deleteBid() {
        Bid bid = new Bid(1, "test", "test", 11.3);
        when(bidListRepository.findById(any())).thenReturn(Optional.of(bid));
        assertTrue(underTest.deleteBid(any()));
    }

    @Test
    void deleteBidShouldReturnFalseIfIdIsNotFound() {
        when(bidListRepository.findById(any())).thenReturn(Optional.empty());
        assertFalse(underTest.deleteBid(any()));
    }

    @Test
    void getBidById() {
        Bid bid = new Bid(1, "test", "test", 11.3);
        when(bidListRepository.findById(any())).thenReturn(Optional.of(bid));
        assertEquals(bid.getAccount(), underTest.getBidById(any()).getAccount());
    }

    @Test
    void getBidByIdShouldReturnNullIfNotFound() {
        when(bidListRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(underTest.getBidById(any()));
    }

    @Test
    void updateBid() {
        Bid bid = new Bid(1, "test", "test", 11.3);
        when(bidListRepository.findById(any())).thenReturn(Optional.of(bid));
        when(bidListRepository.save(any())).thenReturn(bid);
        assertEquals(bid.getAccount(), underTest.updateBid(1, bid).getAccount());
    }

    @Test
    void updateBidShouldReturnNullIfOptionalIsEmpty() {
        when(bidListRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(underTest.updateBid(1, any()));
    }
}