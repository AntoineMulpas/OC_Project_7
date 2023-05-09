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

    public boolean deleteRatingById(Integer id) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            ratingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Rating getRatingById(Integer id) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        return optionalRating.orElse(null);
    }

    public Rating updateRatingById(Integer id, Rating rating) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            optionalRating.get().setMoodysRating(rating.getMoodysRating());
            optionalRating.get().setSandPRating(rating.getSandPRating());
            optionalRating.get().setFitchRating(rating.getFitchRating());
            optionalRating.get().setOrderNumber(rating.getOrderNumber());
            return ratingRepository.save(optionalRating.get());
        }
        return null;
    }

}
