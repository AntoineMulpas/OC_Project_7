package com.example.poseidoninc.repositories;

import com.example.poseidoninc.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
