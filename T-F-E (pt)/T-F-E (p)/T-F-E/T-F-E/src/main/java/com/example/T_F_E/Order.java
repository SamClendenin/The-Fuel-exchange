package com.example.T_F_E;
import org.springframework.ui.Model;
import java.time.LocalDateTime;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "customer_order")
public class Order {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "orderId")

    private java.lang.Long orderId;
    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "listing_id", nullable = false)
    private com.example.T_F_E.Listing listing;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "buyer_id", nullable = false)
    private com.example.T_F_E.User user;


    @jakarta.persistence.Column(nullable = false)
    private java.lang.String cardNumber;

    @jakarta.persistence.Column(nullable = false)
    private java.lang.String cardName;

    @jakarta.persistence.Column(nullable = false)
    private java.lang.String cardExpiration;

    @jakarta.persistence.Column(nullable = false)
    private java.lang.String cvv;

    @jakarta.persistence.Column(nullable = false)
    private java.lang.String deliveryAddress;

    @jakarta.persistence.Column(nullable = false)
    private java.time.LocalDateTime orderDate;

    @jakarta.persistence.Column(nullable = false)
    private java.lang.String status;

    @jakarta.persistence.Column(nullable = false)
    private int quantity;

    @jakarta.persistence.Column(nullable = true)
    private java.time.LocalDateTime deliveryDate;

    public Order(java.lang.String cardNumber, java.lang.String cardName, java.lang.String cardExpiration, java.lang.String cvv, java.lang.String deliveryAddress, java.lang.String status, int quantity) { /* compiled code */ }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    // Getters and Setters


    public Long getId() {
        return orderId;
    }

    public void setId(Long id) {
        this.orderId = id;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public User getBuyer() {
        return user;
    }

    public void setBuyer(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
