package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository; // Inject userRepository

    @GetMapping
    public String viewCart(Model model, @SessionAttribute(value = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }

        Cart cart = cartService.getCart(user);
        model.addAttribute("cart", cart);

        // Calculate total
        double total = cartService.calculateTotal(cart);
        model.addAttribute("total", total);

        return "cart"; // Return the cart view
    }

    // Endpoint to handle adding an item to the cart
    @PostMapping("/add")
    public String addToCart(@RequestParam CartItem productId, @SessionAttribute("user") User user) {
        cartService.addToCart(user, productId);
        return "redirect:/cart"; // Redirect to cart page after adding the item
    }


    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart"; // Redirect to cart view after removing
    }

    @PostMapping("/clear")
    public String clearCart(@SessionAttribute("user") User user) {
        cartService.clearCart(user);
        return "redirect:/cart"; // Redirect to empty cart
    }
}
