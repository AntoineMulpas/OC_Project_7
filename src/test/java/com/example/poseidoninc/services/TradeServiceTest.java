package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Trade;
import com.example.poseidoninc.repositories.TradeRepository;
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
class TradeServiceTest {

    private TradeService underTest;

    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    void setUp() {
        underTest = new TradeService(tradeRepository);
    }

    @Test
    void getAllTrades() {
        when(underTest.getAllTrades()).thenReturn(List.of(new Trade(), new Trade()));
        assertEquals(2, underTest.getAllTrades().size());
    }

    @Test
    void saveTrade() {
        Trade trade = new Trade();
        when(tradeRepository.save(any())).thenReturn(trade);
        assertEquals(trade, underTest.saveTrade(trade));
    }

    @Test
    void findTradeById() {
        when(tradeRepository.findById(any())).thenReturn(Optional.of(new Trade()));
        assertNotNull(underTest.findTradeById(1));
    }

    @Test
    void findTradeByIdShouldReturnNull() {
        when(tradeRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(underTest.findTradeById(1));
    }

    @Test
    void updateTrade() {
        Trade trade = new Trade();
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any())).thenReturn(trade);
        assertEquals(trade, underTest.updateTrade(1, trade));
    }

    @Test
    void updateTradeShouldReturnNull() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.updateTrade(1, new Trade()));
    }

    @Test
    void deleteTradeById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(new Trade()));
        assertTrue(underTest.deleteTradeById(1));
    }

    @Test
    void deleteTradeByIdShouldReturnFalse() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(underTest.deleteTradeById(1));
    }
}