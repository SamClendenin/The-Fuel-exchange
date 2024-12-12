package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @PostMapping("/createListing")
    public String createListing(@RequestParam String title,
                                @RequestParam String fuelType,
                                @RequestParam int quantity,
                                @RequestParam BigDecimal price,
                                @RequestParam String description,
                                @RequestParam int sellerId) {
        Listing newListing = new Listing(title, fuelType, quantity, price, description, sellerId);

        listingService.saveListing(newListing);

        return "redirect:/users/dashboard?role=SELLER&sellerId=" + sellerId;
    }


}
