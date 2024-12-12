package com.example.T_F_E;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListingService listingService;

    @Autowired
    private CartService cartService;

    @GetMapping("/Cart.html")
    public String showCartPage(Model model, User user) {
        Cart cart = cartService.getCart(user);
        model.addAttribute("cart", cart);
        return "Cart"; // Cart.html or a template name you have configured
    }

    @GetMapping("/Buyer-history.html")
    public String showBuyerHistoryPage(Model model) {
        // Add necessary attributes for the buyer's history
        return "Buyer-history"; // Buyer-history.html or corresponding template
    }

    @GetMapping("/Buyer-settings.html")
    public String showBuyerSettingsPage(Model model) {
        // Add necessary attributes for the buyer's settings
        return "Buyer-settings"; // Buyer-settings.html or corresponding template
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home page";
    }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam String role, @RequestParam(required = false) Integer sellerId, @RequestParam(required = false) Integer buyerId, HttpServletRequest request, Model model) {

        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "User not found.");
            return "error";
        }



        model.addAttribute("role", role);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("buyerId", buyerId);

        if ("SELLER".equalsIgnoreCase(role)) {
            if (sellerId != null) {
                List<Listing> sellerListings = listingService.getAllListingsBySellerId(sellerId);
                model.addAttribute("listings", sellerListings);
            }
        } else if ("BUYER".equalsIgnoreCase(role)){
            if (buyerId != null) {
                List<Listing> allListings = listingService.getAllListings();
                model.addAttribute("listings", allListings);
            }
        }

        return "dashboard";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model) {
        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());

            if (user.getRole() == User.Role.BUYER) {
                return "redirect:/users/dashboard?role=BUYER&buyerId=" + user.getUser_Id();
            } else if (user.getRole() == User.Role.SELLER) {
                return "redirect:/users/dashboard?role=SELLER&sellerId=" + user.getUser_Id();
            }
        }

        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }

    @GetMapping("/register/buyer")
    public String showBuyerRegistrationPage(){
        return "Buyer-signup";
    }

    @GetMapping("/register/seller")
    public String showSellerRegistrationPage(){
        return "seller-signup";
    }
    @PostMapping("/register/buyer")
    public String registerBuyer(@ModelAttribute User user, @RequestParam String confirmPassword, Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "Buyer-signup";
        }

        user.setRole(User.Role.BUYER);
        userService.registerUser(user);

        return "redirect:/users/login";
    }

    @PostMapping("/register/seller")
    public String registerSeller(@ModelAttribute User user, @RequestParam String confirmPassword, Model model) {
        System.out.println("Received request to register seller: " + user.getUsername());
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "seller-signup";
        }

        if (user.getCompanyName() == null || user.getCompanyName().isEmpty()) {
            model.addAttribute("error", "Company name is required.");
            return "seller-signup";
        }

        if (user.getCompanyDescription() == null || user.getCompanyDescription().isEmpty()) {
            model.addAttribute("error", "Company description is required.");
            return "seller-signup";
        }

        user.setRole(User.Role.SELLER);
        userService.registerUser(user);

        return "redirect:/users/login";
    }

    @GetMapping("/profile/{username}")
    public String showProfile(@PathVariable("username") String username, Model model) {

        User user = userService.findByUsername(username);

        if (user != null) {
            model.addAttribute("user", user);

            model.addAttribute("role", user.getRole().toString());
            if (user.getRole() == User.Role.SELLER) {
                model.addAttribute("sellerId", user.getUser_Id());
            } else if (user.getRole() == User.Role.BUYER) {
                model.addAttribute("buyerId", user.getUser_Id());
            }
            return "profile";
        }

        model.addAttribute("error", "User not found.");
        return "error";
    }

    @GetMapping("/editProfile")
    public String showEditProfilePage(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("role", user.getRole().toString());
            return "editProfile";
        }
        return "redirect:/users/login";
    }

    @PostMapping("/updateProfile")
    public String updateUser(@ModelAttribute User updatedUser, HttpSession session, Model model) {
        String currentUsername = (String) session.getAttribute("username");
        if (currentUsername == null) {
            return "redirect:/users/login";
        }

        try {
            User updated = userService.updateUser(currentUsername, updatedUser);

            if (!currentUsername.equals(updated.getUsername())) {
                session.setAttribute("username", updated.getUsername());
            }

            return "redirect:/users/profile/" + updated.getUsername();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/deleteAccount/{user_Id}")
    public String deleteAccount(@PathVariable("user_Id") int user_Id) {
        userService.deleteUser(user_Id);
        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/users/login";
    }


}
