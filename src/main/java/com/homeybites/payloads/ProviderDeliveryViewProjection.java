package com.homeybites.payloads;

import java.time.LocalDate;

public interface ProviderDeliveryViewProjection {
	Long getDeliveryId();

    Long getSubscriptionId();

    Long getUserId();

    MealType getMealType();

    String getFoodItems();

    LocalDate getDeliveryDate();

    String getReceiverName();

    String getReceiverContactNo();

    String getDeliveryAddress();

    DailyDeliveryStatus getStatus();
}
