package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Autowired
    private ListingRepository listingRepository;

    public long getTotalSalesForSeller(int sellerId) {
        return analyticsRepository.countBySellerId(sellerId);
    }

    // Get total revenue for a seller
    public BigDecimal getTotalRevenueForSeller(int sellerId) {
        return analyticsRepository.sumTotalPriceBySellerId(sellerId);
    }

    // Get the product with the most sales for a seller
    public Listing getTopSellingProduct(int sellerId) {
        Integer topListingId = analyticsRepository.findTopSellingListingBySellerId(sellerId);

        if (topListingId == null) {
            return null; // No top-selling product
        }

        return listingRepository.findById(topListingId).orElse(null);
    }

    // Get all sales data for a seller
    public List<Analytics> getAnalyticsForSeller(int sellerId) {
        return analyticsRepository.findBySellerId(sellerId);
    }

    public List<MonthlySalesData> getMonthlySalesData(int sellerId) {
        return analyticsRepository.findMonthlySalesData(sellerId);
    }


}
