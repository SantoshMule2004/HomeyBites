package com.homeybites.payloads;

import java.util.List;

public class CartDto {
    private List<CartItemDto> cartItems;
    private Double grandTotal;
    
    public CartDto(List<CartItemDto> cartItems, Double grandTotal) {
        this.cartItems = cartItems;
        this.grandTotal = grandTotal;
    }
    
    public List<CartItemDto> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }
    public Double getGrandTotal() {
        return grandTotal;
    }
    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
