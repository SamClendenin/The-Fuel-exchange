package com.example.T_F_E;

import jakarta.persistence.Entity;

import java.math.BigDecimal;


public class MonthlySalesData {
    private int month;
    private int year;
    private BigDecimal totalRevenue;

    public MonthlySalesData(int month, int year, BigDecimal totalRevenue) {
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    // Getters and setters


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
