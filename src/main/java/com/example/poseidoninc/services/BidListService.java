package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This Class is the Service layer for the Bid object.
 * It is annotated with the @Transaction to manage transactions of
 * all the methods contained in this class.
 */

@Service
@Transactional
public class BidListService {

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * This method is used to retrieve all bids.
     * @return List of all bids.
     */

    public List <Bid> findAllBids() {
        return bidListRepository.findAll();
    }

    /**
     * This method is used to save a new bid.
     * @param bid
     * @return It returns the saved bid.
     */

    public Bid saveNewBid(Bid bid) {
        return bidListRepository.save(bid);
    }

    /**
     * This method is used to delete a specific bid.
     * @param id
     * @return it returns a boolean depending on the outcome of the operation
     */

    public boolean deleteBid(Integer id) {
        Optional <Bid> bidListRepositoryById = bidListRepository.findById(id);
        if (bidListRepositoryById.isPresent()) {
            bidListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used to get a specific bid depending on the id.
     * @param id
     * @return a specific bid found by id or null.
     */

    public Bid getBidById(Integer id) {
        Optional <Bid> optionalBid = bidListRepository.findById(id);
        return optionalBid.orElse(null);
    }

    /**
     * This method is used to update to bid.
     * @param id
     * @param bid
     * @return it returns the update bid in case of success
     * or null if no bid for the specific id exists.
     */

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
