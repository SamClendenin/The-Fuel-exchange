package com.example.T_F_E;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping({"/orders"})
public class OrderController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.example.T_F_E.OrderService orderService;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    private com.example.T_F_E.ListingService listingService;

    public OrderController() { /* compiled code */ }

    // Place a new order
    @PostMapping("/create")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        // Get the username from the 'buyer' property of the 'Order'
        User user = userService.findByUsername(order.getBuyer().getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // User not found, return bad request
        }

        // Set the user (buyer) in the order
        order.setBuyer(user);

        // Assuming you have a method in orderService to place the order
        Order savedOrder = orderService.placeOrder(order);

        // Return the saved order as the response
        return ResponseEntity.ok(savedOrder);
    }


//    @PostMapping("/create")
//    public String placeOrder(@RequestParam String cardNumber,
//                             @RequestParam String cardName,
//                             @RequestParam String cardExpiration,
//                             @RequestParam String cvv,
//                             @RequestParam String deliveryAddress,
//                             @RequestParam int quantity,
//                             HttpServletRequest request,
//                             Model model) {
//        String username = (String) request.getSession().getAttribute("username");
//
//        // Retrieve the logged-in user from the database
//        User user = userService.findByUsername(username);
//
//        if (user == null) {
//            model.addAttribute("error", "User not found.");
//            return "error"; // Or redirect to login
//        }
//
//        // Assuming a listing is selected on the page, you could retrieve the listing
//        // from the session or pass it as a parameter to this endpoint.
//        Listing listing = listingService.getListingById(1L);  // Replace with actual listing ID
//
//        // Create and save the order
//        Order order = new Order(cardNumber, cardName, cardExpiration, cvv, deliveryAddress,
//                "PENDING", quantity); // Order status could be "PENDING"
//        order.setUser(user);  // Set the user
//        order.setListing(listing);  // Set the listing
//
//        Order savedOrder = orderService.placeOrder(order);  // Save the order to the database
//
//        model.addAttribute("order", savedOrder);
//        return "orderConfirmation";  // Redirect to order confirmation page
//    }

    @GetMapping("/history")
    public String viewOrderHistory(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findByUsername(username);

        if (user != null) {
            List<Order> orders = orderService.getOrdersByUser(user);
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("error", "User not found.");
        }

        return "buyer-history";  // Page displaying the user's order history
    }


    // View all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get a specific order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an order
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        return orderService.getOrderById(orderId)
                .map(existingOrder -> {
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setDeliveryDate(order.getDeliveryDate());
                    existingOrder.setQuantity(order.getQuantity());
                    existingOrder.setListing(order.getListing());
                    Order updatedOrder = orderService.updateOrder(existingOrder);
                    return ResponseEntity.ok(updatedOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Cancel an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }


}
