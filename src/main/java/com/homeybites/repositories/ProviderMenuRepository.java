package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.ProviderMenu;
import com.homeybites.payloads.ProviderMenuProjection;

public interface ProviderMenuRepository extends JpaRepository<ProviderMenu, Long> {
	List<ProviderMenu> findByProviderId(Long providerId);

	// Crucial for the Bulk-Replace strategy
	@Modifying
	void deleteByProviderId(Long providerId);
	
	@Query(value = """
            SELECT
                pm.id,
                pm.provider_id AS providerId,
                pm.day_of_week AS dayOfWeek,
                pm.is_active AS isActive
            FROM provider_menus pm
            WHERE pm.provider_id = :providerId
            ORDER BY FIELD(pm.day_of_week,
                'MONDAY',
                'TUESDAY',
                'WEDNESDAY',
                'THURSDAY',
                'FRIDAY',
                'SATURDAY',
                'SUNDAY')
            """, nativeQuery = true)
    List<ProviderMenuProjection> findMenusByProviderId(Long providerId);
	
	Optional<ProviderMenu> findByIdAndProviderId(Long id, Long providerId);
}
