package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.NearbyMenuProjection;
import com.homeybites.payloads.MenuProjection;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	// get all menu items by category
	List<MenuItem> findByCategoryId(Long categoryId);

	@Query(value = """
			SELECT
			    mi.menu_id AS menuId,
			    mi.menu_name AS menuName,
			    mi.price,
			    mi.description,
			    mi.count,
			    mi.is_active AS isActive,
			    mi.menu_type AS menuType,
			    mi.image_public_id AS imagePublicId,
			    mi.image_url AS imageUrl,
			    mi.format,
			    mi.category_id AS categoryId,
			    c.category_name AS categoryName,
			    c.is_active AS categoryIsActive,
			    mi.provider_id AS providerId,
			    mi.created_at AS createdAt
			FROM menu_item mi
			LEFT JOIN category c
			    ON mi.category_id = c.category_id
			WHERE mi.provider_id = :providerId
			  AND (:menuType IS NULL OR mi.menu_type = :menuType)
			  AND (:isActive IS NULL OR mi.is_active = :isActive)
			  AND (:categoryId IS NULL OR mi.category_id = :categoryId)
			  AND (:search IS NULL OR LOWER(mi.menu_name) LIKE LOWER(CONCAT('%', :search, '%')))
			ORDER BY mi.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)
			FROM menu_item mi
			LEFT JOIN category c
			    ON mi.category_id = c.category_id
			WHERE mi.provider_id = :providerId
			  AND (:menuType IS NULL OR mi.menu_type = :menuType)
			  AND (:isActive IS NULL OR mi.is_active = :isActive)
			  AND (:categoryId IS NULL OR mi.category_id = :categoryId)
			  AND (:search IS NULL OR LOWER(mi.menu_name) LIKE LOWER(CONCAT('%', :search, '%')))
			""", nativeQuery = true)
	Page<MenuProjection> getMenusByProvider(@Param("providerId") Long providerId, @Param("menuType") String menuType,
			@Param("isActive") Boolean isActive, @Param("categoryId") Long categoryId, @Param("search") String search,
			Pageable pageable);

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
	NearbyMenuProjection getMenuItemById(@Param("menuId") Long menuId, @Param("lat") double userLat,
			@Param("lng") double userLng);

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
			    ST_Distance_Sphere(
			        u.provider_location_point,
			        ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)
			    ) AS distanceInMeters
			FROM menu_item m
			JOIN `user` u ON m.provider_id = u.user_id
			WHERE
			    MBRContains(
			        ST_Buffer(
			            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326),
			            :maxLimit
			        ),
			        u.provider_location_point
			    )
			    AND ST_Distance_Sphere(
			            u.provider_location_point,
			            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)
			        ) <= u.service_radius
			    AND m.is_active = true
			    AND (:categoryId IS NULL OR m.category_id = :categoryId)
			    AND (:menuType IS NULL OR m.menu_type = :menuType)
			    AND (:maxPrice IS NULL OR m.price <= :maxPrice)
			ORDER BY distanceInMeters ASC
			""",

			countQuery = """
					SELECT COUNT(*)
					FROM menu_item m
					JOIN `user` u ON m.provider_id = u.user_id
					WHERE
					    MBRContains(
					        ST_Buffer(
					            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326),
					            :maxLimit
					        ),
					        u.provider_location_point
					    )
					    AND ST_Distance_Sphere(
					            u.provider_location_point,
					            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)
					        ) <= u.service_radius
					    AND m.is_active = true
					    AND (:categoryId IS NULL OR m.category_id = :categoryId)
					    AND (:menuType IS NULL OR m.menu_type = :menuType)
					    AND (:maxPrice IS NULL OR m.price <= :maxPrice)
					""",

			nativeQuery = true)
	Page<NearbyMenuProjection> findProviderNearbyUsers(@Param("lat") double userLat, @Param("lng") double userLng,
			@Param("maxLimit") double maxAbsolutePlatformRadiusInMeters, @Param("categoryId") Long categoryId,
			@Param("menuType") String menuType, @Param("maxPrice") Double maxPrice, Pageable pageable);

	@Query("""
			SELECT COUNT(m)
			FROM MenuItem m
			WHERE m.providerId = :providerId
			""")
	Long countMenuItems(Long providerId);

	@Query("""
			SELECT COUNT(m)
			FROM MenuItem m
			WHERE m.providerId = :providerId
			AND m.isActive = :isActive
			""")
	Long countActiveMenuItems(Long providerId, Boolean isActive);
}
