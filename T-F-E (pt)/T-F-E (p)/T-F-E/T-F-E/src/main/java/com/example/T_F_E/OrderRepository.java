package com.example.T_F_E;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom query to find orders by user
    List<Order> findByUser(User user);
}
