package com.homeybites.payloads;

import java.math.BigDecimal;

public interface OrderItemProjection {

    Long getOrderItemId();

    Long getProviderOrderId();

    Long getMenuItemId();

    String getItemName();

    BigDecimal getPurchasedPrice();

    Integer getQuantity();

    BigDecimal getTotalPrice();
}
