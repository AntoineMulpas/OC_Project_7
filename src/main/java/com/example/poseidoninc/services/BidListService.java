package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List <Bid> findAllBids() {
        return bidListRepository.findAll();
    }

    public Bid saveNewBid(Bid bid) {
        return bidListRepository.save(bid);
    }


}
