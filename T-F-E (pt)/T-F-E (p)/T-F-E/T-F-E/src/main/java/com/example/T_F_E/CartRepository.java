package com.example.T_F_E;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    //Cart findByUser(User user);  // Retrieve cart by user
    Optional<Cart> findByUser(User user);  // This returns Optional<Cart>
}
