package com.example.T_F_E;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByCart(Cart cart);  // Delete all items for a specific cart
}
