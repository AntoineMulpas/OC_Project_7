package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.Rating;
import com.example.poseidoninc.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This Class is the Service layer for the Rating object.
 * It is annotated with the @Transaction to manage transactions of
 * all the methods contained in this class.
 */
@Service
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * This method is used to add a new Rating.
     * @param rating
     * @return It returns the saved Rating.
     */

    public Rating addNewRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * This method is used to retrive all Ratings.
     * @return List of all ratings.
     */

    public List <Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * This method is used to delete a specific rating.
     * @param id
     * @return it returns a boolean depending on the outcome of the operation.
     */

    public boolean deleteRatingById(Integer id) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            ratingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * This method is used to retrieve a specific rating by id.
     * @param id
     * @return a specific rating found by id or null.
     */

    public Rating getRatingById(Integer id) {
        Optional <Rating> optionalRating = ratingRepository.findById(id);
        return optionalRating.orElse(null);
    }

    /**
     * This method is used to update a specific Rating
     * @param id
     * @param rating
     * @return the updated Rating in case of success or null.
     */

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
