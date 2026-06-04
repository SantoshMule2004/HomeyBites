package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.NearbyMenuProjection;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // get all menu items by category
    List<MenuItem> findByCategoryId(Long categoryId);

    // get all menu items of tiffin provider
    List<MenuItem> findByProviderId(Long providerId);

    @Query("SELECT m FROM MenuItem m WHERE m.providerId=:providerId AND m.isActive = true")
    List<MenuItem> getMenuItemByuserAndActive(@Param("providerId") Long providerId);

    // Optional: Only get active items from those providers
    List<MenuItem> findByProviderIdInAndIsActiveTrue(List<Integer> providerIds);

    List<MenuItem> findByMenuType(String menuType);

    // get menu item by id
    @Query(value = """
                SELECT
                    m.menu_id AS menuId,
                    m.menu_name AS menuName,
                    m.price AS price,
                    m.description AS description,
                    m.count AS count,
                    m.is_active AS isActive,
                    m.menu_type AS menuType,
                    m.image_public_id AS imagePublicId,
                    m.image_url AS imageUrl,
                    m.format AS format,
                    m.category_id AS categoryId,
                    u.user_id AS providerId,
                    u.business_name AS businessName,
                    u.latitude AS latitude,
                    u.longitude AS longitude,
                    u.service_radius AS serviceRadius,
                    ST_Distance_Sphere(u.provider_location_point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) AS distanceInMeters
                FROM menu_item m
                JOIN `user` u ON m.provider_id = u.user_id
                WHERE m.menu_id = :menuId
            """, nativeQuery = true)
    NearbyMenuProjection getMenuItemById(
            @Param("menuId") Long menuId,
            @Param("lat") double userLat,
            @Param("lng") double userLng);


    @Query(value = "SELECT " +
            "  m.menu_id AS menuId, " +
            "  m.menu_name AS menuName, " +
            "  m.price AS price, " +
            "  m.description AS description, " +
            "  m.count AS count, " +
            "  m.is_active AS isActive, " +
            "  m.menu_type AS menuType, " +
            "  m.image_public_id AS imagePublicId, " +
            "  m.image_url AS imageUrl, " +
            "  m.format AS format, " +
            "  m.category_id AS categoryId, " +
            "  u.user_id AS providerId, " +
            "  u.business_name AS businessName, " +
            "  u.latitude AS latitude, " +
            "  u.longitude AS longitude, " +
            "  u.service_radius AS serviceRadius, " +
            "  ST_Distance_Sphere(u.provider_location_point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) AS distanceInMeters "
            +
            "FROM menu_item m " +
            "JOIN `user` u ON m.provider_id = u.user_id " +
            "WHERE " +
            // LAYER 1: Hit the spatial index using a mathematically accurate bounding
            // envelope
            "MBRContains(" +
            "  ST_Buffer(ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326), :maxLimit)," +
            "  u.provider_location_point" +
            ") " +
            // LAYER 2: Apply precise delivery rule matching against the specific provider's
            // radius
            "AND ST_Distance_Sphere(u.provider_location_point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) <= u.service_radius "
            +
            "AND m.is_active = true " +
            // --- NEW DYNAMIC FILTERS ---
            // 3. Category Filter
            "AND (:categoryId IS NULL OR m.category_id = :categoryId) " +
            // 4. Menu Type Filter (e.g., "VEG", "NON_VEG")
            "AND (:menuType IS NULL OR m.menu_type = :menuType) " +
            // 5. Max Price Filter
            "AND (:maxPrice IS NULL OR m.price <= :maxPrice) " +
            "ORDER BY distanceInMeters ASC", nativeQuery = true)
    List<NearbyMenuProjection> findProviderNearbyUsers(
            @Param("lat") double userLat,
            @Param("lng") double userLng,
            @Param("maxLimit") double maxAbsolutePlatformRadiusInMeters,
            @Param("categoryId") Long categoryId,
            @Param("menuType") String menuType,
            @Param("maxPrice") Double maxPrice);
}
