package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//import static sun.security.pkcs11.wrapper.*;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository; // Inject userRepository


    @Autowired
    private ListingRepository listingRepository;

    // Retrieve the user's cart or throw an exception if not found
    public Cart getCart(User user) {
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        return cartOptional.orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getUsername()));
    }

    // Add item to the cart
    public void addToCart(User user, CartItem cartItem) {
        if (userRepository.existsById(user.getUser_Id())) {
            // Proceed with cart operations

        // Proceed with adding items to the cart
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);  // Save the new cart
                });

        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
            updateCartTotal(cart);
        } else {
            throw new RuntimeException("User does not exist!");
        }
    }

    // Remove a CartItem from the cart
    public void removeFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        Cart cart = cartItem.getCart();
        cartItemRepository.delete(cartItem);
        updateCartTotal(cart);
    }

    // Update the total price of the cart
    public void updateCartTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
                .sum();
        cart.setTotal(total);
        cartRepository.save(cart);
    }

    // Calculate the total price for the cart
    public double calculateTotal(Cart cart) {
        double total = 0.0;
        for (CartItem item : cart.getItems()) {
            total += item.getPrice().doubleValue() * item.getQuantity();
        }
        return total;
    }

    // Clear the cart (delete it from the database)
    public void clearCart(User user) {
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        Cart cart = cartOptional.orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getUsername()));
        cartRepository.delete(cart);  // Delete the cart from the database
    }
}
