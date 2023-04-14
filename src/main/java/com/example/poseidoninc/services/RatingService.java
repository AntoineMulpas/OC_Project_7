package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Rating;
import com.example.poseidoninc.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating addNewRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List <Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    public void deleteRatingById(Integer id) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            ratingRepository.deleteById(id);
        }
    }

    public Rating getRatingById(Integer id) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        return optionalRating.orElse(null);
    }

    public void updateRatingById(Integer id, Rating rating) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            Rating ratingToBeUpdated = optionalRating.get();
            ratingToBeUpdated.setMoodysRating(rating.getMoodysRating());
            ratingToBeUpdated.setSandPRating(rating.getSandPRating());
            ratingToBeUpdated.setFitchRating(rating.getFitchRating());
            ratingToBeUpdated.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(ratingToBeUpdated);
        }
    }

}
