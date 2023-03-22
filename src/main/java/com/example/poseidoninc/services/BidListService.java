package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.BidList;
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

    public List <BidList> findAllBids() {
        return bidListRepository.findAll();
    }


}
