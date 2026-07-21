package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.ProviderMenuItem;
import com.homeybites.payloads.MealType;
import com.homeybites.payloads.ProviderMenuItemProjection;

public interface ProviderMenuItemRepository extends JpaRepository<ProviderMenuItem, Long> {
	@Query(value = """
			SELECT
			    pmi.id,
			    pmi.provider_menu_id AS providerMenuId,
			    pmi.meal_type AS mealType,
			    pmi.food_items AS foodItems
			FROM provider_menu_item pmi
			WHERE pmi.provider_menu_id IN (:menuIds)
			ORDER BY pmi.provider_menu_id,
			         FIELD(pmi.meal_type,
			            'BREAKFAST',
			            'LUNCH',
			            'DINNER')
			""", nativeQuery = true)
	List<ProviderMenuItemProjection> findByProviderMenuIds(List<Long> menuIds);

	Optional<ProviderMenuItem> findByProviderMenuIdAndMealType(Long providerMenuId, MealType mealType);

	List<ProviderMenuItem> findByProviderMenuId(Long providerMenuId);

	@Modifying
	@Query(value = """
			DELETE FROM provider_menu_item
			WHERE provider_menu_id = :providerMenuId
			""", nativeQuery = true)
	void deleteByProviderMenuId(Long providerMenuId);

	@Query(value = """
			SELECT pmi.*
			FROM provider_menu_item pmi
			JOIN provider_menus pm
			    ON pm.id = pmi.provider_menu_id
			WHERE pm.provider_id = :providerId
			  AND pm.day_of_week = :dayOfWeek
			  AND pmi.meal_type = :mealType
			""", nativeQuery = true)
	Optional<ProviderMenuItem> findMenuForDelivery(@Param("providerId") Long providerId,
			@Param("dayOfWeek") String dayOfWeek, @Param("mealType") String mealType);
}
