package com.example.T_F_E;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer>{
    List<Listing> findBySellerId(int sellerId);

    Listing findByListingId(int listingId);

    Optional<Listing> findByTitle(String title);
}
