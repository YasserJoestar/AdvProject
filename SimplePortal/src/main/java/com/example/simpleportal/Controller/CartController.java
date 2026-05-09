package com.example.simpleportal.Controller;

import com.example.simpleportal.Model.Cart;
import com.example.simpleportal.Model.CartItem;
import com.example.simpleportal.Service.ActivityLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    public static final String CART_SESSION_KEY = "cartItems";

    private final ActivityLogService logService;

    public CartController(ActivityLogService logService) {
        this.logService = logService;
    }

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        List<CartItem> items = getCart(session);
        model.addAttribute(CART_SESSION_KEY, items);
        model.addAttribute("total", total(items));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam String type,
            @RequestParam(required = false) Long productRefId,
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam(defaultValue = "/cart") String redirectTo,
            @CookieValue(name = "student_id", required = false) String cookieId,
            HttpSession session) {

        List<CartItem> items = getCart(session);
        CartItem existing = items.stream()
                .filter(i -> i.getType().equalsIgnoreCase(type)
                        && (productRefId == null ? i.getProductRefId() == null : productRefId.equals(i.getProductRefId()))
                        && i.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);

        if (existing == null) {
            items.add(new CartItem(type, productRefId, name, price, 1));
        } else {
            existing.setQuantity(existing.getQuantity() + 1);
        }
        saveCart(session, items);
        logService.log(cookieId, "ADD_TO_CART", "Added to cart: " + name + " (" + type + ")");
        return "redirect:" + redirectTo;
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(
            @RequestParam int index,
            @CookieValue(name = "student_id", required = false) String cookieId,
            HttpSession session) {

        List<CartItem> items = getCart(session);
        if (index >= 0 && index < items.size()) {
            String removed = items.get(index).getName();
            items.remove(index);
            logService.log(cookieId, "REMOVE_FROM_CART", "Removed from cart: " + removed);
        }
        saveCart(session, items);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(
            @CookieValue(name = "student_id", required = false) String cookieId,
            HttpSession session) {
        logService.log(cookieId, "CLEAR_CART", "Cart cleared");
        session.setAttribute(CART_SESSION_KEY, new Cart());
        return "redirect:/cart";
    }

    // ── helpers ────────────────────────────────────────────────
    List<CartItem> getCart(HttpSession session) {
        Object obj = session.getAttribute(CART_SESSION_KEY);
        if (obj instanceof Cart c) return c.getItems();
        Cart c = new Cart();
        session.setAttribute(CART_SESSION_KEY, c);
        return c.getItems();
    }

    private void saveCart(HttpSession session, List<CartItem> items) {
        Cart c = new Cart(); c.setItems(items);
        session.setAttribute(CART_SESSION_KEY, c);
    }

    private BigDecimal total(List<CartItem> items) {
        return items.stream().map(CartItem::getLineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
