package com.example.T_F_E;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int listingId;

    @Column(nullable = false)
    private String title;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 500)
    private String description;

    @Column(name = "seller_id", nullable = false)
    protected int sellerId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Listing() {
    }

    public Listing(String title, String fuelType, int quantity, BigDecimal price, String description, int sellerId) {
        this.title = title;
        this.fuelType = fuelType;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;

    }


    public int getListingId() {

        return listingId;
    }
    public void setListingId(int listingId) {

        this.listingId = listingId;
    }

    public String getTitle() {

        return title;
    }
    public void setTitle(String title) {

        this.title = title;
    }

    public String getFuelType() {

        return fuelType;
    }
    public void setFuelType(String fuelType) {

        this.fuelType = fuelType;
    }

    public int getQuantity() {

        return quantity;
    }
    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public BigDecimal getPrice() {

        return price;
    }
    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public String getDescription() {

        return description;
    }
    public void setDescription(String description) {

        this.description = description;
    }

    public int getSellerId() {

        return sellerId;
    }
    public void setSellerId(int sellerId) {

        this.sellerId = sellerId;
    }

}
