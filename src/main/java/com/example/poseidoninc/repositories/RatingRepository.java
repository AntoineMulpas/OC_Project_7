package com.example.poseidoninc.repositories;

import com.example.poseidoninc.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
