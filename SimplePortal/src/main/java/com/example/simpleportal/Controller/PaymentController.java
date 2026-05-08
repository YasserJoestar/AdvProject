package com.example.university;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PaymentController {

    private static final String PAYMENT_MODEL_KEY = "payment";
    private static final String VIEW_PAYMENT = "payment";
    private static final String VIEW_ORDER_SUMMARY = "ordersummary";
    private static final String VIEW_SUCCESS = "success";

    @GetMapping("/payment")
    public String payment(
            @RequestParam(required = false) String item,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String price,
            @RequestParam(required = false, defaultValue = "false") boolean cart,
            HttpSession session,
            Model model
    ) {
        model.addAttribute(PAYMENT_MODEL_KEY, new PaymentForm());
        model.addAttribute("item", item);
        model.addAttribute("type", type);
        model.addAttribute("price", price);
        if (cart) {
            List<CartItem> cartItems = getCartItems(session);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartTotal", calculateCartTotal(cartItems));
        }
        return VIEW_PAYMENT;
    }

    @PostMapping("/processPayment")
    public String processPayment(
            @ModelAttribute(PAYMENT_MODEL_KEY) PaymentForm payment,
            HttpSession session,
            Model model
    ) {
        List<CartItem> cartItems = getCartItems(session);
        int fees = calculateCartTotal(cartItems).intValue();
        if (fees == 0) {
            fees = 10000;
        }
        int discount = 0;
        int total = fees - discount;

        model.addAttribute(PAYMENT_MODEL_KEY, payment);
        model.addAttribute("fees", fees);
        model.addAttribute("discount", discount);
        model.addAttribute("total", total);
        model.addAttribute("cartItems", cartItems);

        return VIEW_ORDER_SUMMARY;
    }

    @PostMapping("/confirmPayment")
    public String confirmPayment(HttpSession session) {
        session.setAttribute(CartController.CART_SESSION_KEY, new Cart());
        return "redirect:/payment/success";
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return VIEW_SUCCESS;
    }

    private List<CartItem> getCartItems(HttpSession session) {
        Object cart = session.getAttribute(CartController.CART_SESSION_KEY);
        if (cart instanceof Cart sessionCart) {
            return sessionCart.getItems();
        }
        return List.of();
    }

    private BigDecimal calculateCartTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

