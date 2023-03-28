package com.example.poseidoninc.repositories;

import com.example.poseidoninc.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends JpaRepository<Bid, Integer> {


}
