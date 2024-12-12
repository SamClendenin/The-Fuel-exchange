package com.example.T_F_E;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard/{sellerId}")
    public String getAnalyticsPage(@PathVariable int sellerId, HttpServletRequest request, Model model) {

        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findByUsername(username);
        long totalSales = analyticsService.getTotalSalesForSeller(sellerId);
        BigDecimal totalRevenue = analyticsService.getTotalRevenueForSeller(sellerId);
        Listing topSellingProduct = analyticsService.getTopSellingProduct(sellerId);
        List<MonthlySalesData> monthlySalesData = analyticsService.getMonthlySalesData(sellerId);

        model.addAttribute("role", user.getRole().toString());
        model.addAttribute("sellerId", user.getUser_Id());
        model.addAttribute("user", user);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("topSellingProduct", topSellingProduct);
        model.addAttribute("monthlySalesData", monthlySalesData);


        return "seller-analytics";
    }
}
