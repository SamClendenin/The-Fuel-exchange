package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<Listing> getAllListingsBySellerId(int sellerId) {
        return listingRepository.findBySellerId(sellerId);
    }


    public Listing getListingById(int listingId) {
        return listingRepository.findByListingId(listingId);
    }

    public Listing updateListing(int listingId, Listing updatedListing) {
        Listing existingListing = getListingById(listingId);
        existingListing.setTitle(updatedListing.getTitle());
        existingListing.setFuelType(updatedListing.getFuelType());
        existingListing.setQuantity(updatedListing.getQuantity());
        existingListing.setPrice(updatedListing.getPrice());
        existingListing.setDescription(updatedListing.getDescription());
        return listingRepository.save(existingListing);
    }

    public void deleteListing(int listingId) {
        listingRepository.deleteById(listingId);
    }

    public void saveListing(Listing listing) {
        listingRepository.save(listing);
    }
}
