package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Trade;
import com.example.poseidoninc.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This Class is the Service layer for the Trade object.
 * It is annotated with the @Transaction to manage transactions of
 * all the methods contained in this class.
 */

@Service
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * This method is used to retrieve all trades.
     * @return List of all trades.
     */

    public List <Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    /**
     * This method is used to save a new trade.
     * @param trade
     * @return It returns the saved trade.
     */

    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * This method is used to find a specific trade by id.
     * @param id
     * @return Trade or null.
     */

    public Trade findTradeById(Integer id) {
        return tradeRepository.findById(id).orElse(null);
    }

    /**
     * This method is used to update a specific trade.
     * @param id
     * @param trade
     * @return Trade or null.
     */

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

    /**
     * This method is used to delete a specific trade.
     * @param id
     * @return a boolean depending on the outcome of the operation.
     */

    public boolean deleteTradeById(Integer id) {
        if (tradeRepository.findById(id).isPresent()) {
            tradeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
