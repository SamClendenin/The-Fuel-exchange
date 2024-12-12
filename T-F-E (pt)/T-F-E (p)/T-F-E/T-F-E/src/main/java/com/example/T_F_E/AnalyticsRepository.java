package com.example.T_F_E;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {

    long countBySellerId(int sellerId);

    @Query("SELECT SUM(a.totalPrice) FROM Analytics a WHERE a.sellerId = :sellerId")
    BigDecimal sumTotalPriceBySellerId(int sellerId);

    @Query("SELECT a.listingId FROM Analytics a WHERE a.sellerId = :sellerId " +
            "GROUP BY a.listingId " +
            "ORDER BY SUM(a.quantity) DESC LIMIT 1")
    Integer findTopSellingListingBySellerId(@Param("sellerId") int sellerId);

    // Get all analytics for a specific seller
    List<Analytics> findBySellerId(int sellerId);

    @Query("SELECT new com.example.T_F_E.MonthlySalesData(" + "MONTH(a.saleDate), " +
            "YEAR(a.saleDate), " + "SUM(a.totalPrice)) " + // Sum of total price for that month
            "FROM Analytics a " +
            "WHERE a.sellerId = :sellerId " +
            "GROUP BY YEAR(a.saleDate), MONTH(a.saleDate) " + // Group by year and month
            "ORDER BY YEAR(a.saleDate) DESC, MONTH(a.saleDate) DESC")
    List<MonthlySalesData> findMonthlySalesData(int sellerId);


}
