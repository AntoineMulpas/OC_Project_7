package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Trade;
import com.example.poseidoninc.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    public List <Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade findTradeById(Integer id) {
        return tradeRepository.findById(id).orElse(null);
    }

    public Trade updateTrade(Integer id, Trade trade) {
        Optional <Trade> optionalTrade = tradeRepository.findById(id);
        if (optionalTrade.isPresent()) {
            Trade tradeToUpdate = optionalTrade.get();
            tradeToUpdate.setAccount(trade.getAccount());
            tradeToUpdate.setType(trade.getType());
            tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
            return tradeRepository.save(tradeToUpdate);
        }
        return null;
    }

    public boolean deleteTradeById(Integer id) {
        if (tradeRepository.findById(id).isPresent()) {
            tradeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
