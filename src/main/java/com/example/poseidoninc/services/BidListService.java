package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public boolean deleteBid(Integer id) {
        Optional <Bid> bidListRepositoryById = bidListRepository.findById(id);
        if (bidListRepositoryById.isPresent()) {
            bidListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Bid getBidById(Integer id) {
        Optional <Bid> optionalBid = bidListRepository.findById(id);
        return optionalBid.orElse(null);
    }

    public Bid updateBid(Integer id, Bid bid) {
        Optional <Bid> optionalBid = bidListRepository.findById(id);
        if (optionalBid.isPresent()) {
            Bid bidToSave = optionalBid.get();
            bidToSave.setAccount(bid.getAccount());
            bidToSave.setType(bid.getType());
            bidToSave.setBidQuantity(bid.getBidQuantity());
            return bidListRepository.save(bidToSave);
        }
        return null;
    }

}
