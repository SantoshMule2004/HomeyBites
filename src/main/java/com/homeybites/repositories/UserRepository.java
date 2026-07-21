package com.homeybites.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.User;
import com.homeybites.payloads.BusinessDetailsProjection;
import com.homeybites.payloads.RecentProviderProjection;
import com.homeybites.payloads.RecentUserProjection;
import com.homeybites.payloads.UserInfo;

public interface UserRepository extends JpaRepository<User, Long> {

	// find user by email id
	Optional<User> findByEmailId(String emailId);

	// is user present by email id
	boolean existsByEmailId(String username);

	@Query("""
			SELECT new com.homeybites.payloads.UserInfo(
			    u.userId,
			    u.firstName,
			    u.middleName,
			    u.lastName,
			    u.emailId,
			    u.isVerified,
			    u.phoneNo,
			    u.dob,
			    u.gender,
			    u.dietryPref,
			    u.userRole,
			    u.businessName,
			    u.foodLicenseNo,
			    u.GSTIN,
			    u.latitude,
			    u.longitude,
			    u.serviceRadius
			)
			FROM User u
			WHERE u.emailId = :emailId
			""")
	Optional<UserInfo> findUserByEmail(@Param("emailId") String emailId);

	@Query(value = """
			SELECT
			    u.business_name AS businessName,
			    u.food_license_no AS foodLicenseNo,
			    u.gstin AS GSTIN,
			    u.latitude AS latitude,
			    u.longitude AS longitude,
			    u.service_radius AS serviceRadius,

			    a.address_line AS addressLine,
			    a.area AS area

			FROM user u
			LEFT JOIN address a
			    ON a.user_id = u.user_id

			WHERE u.user_id = :userId
			""", nativeQuery = true)
	BusinessDetailsProjection getBusinessDetailsOfProvider(@Param("userId") Long userId);

	@Query(value = """
			SELECT
			    u.user_id AS userId,
			    u.first_name AS firstName,
			    u.middle_name AS middleName,
			    u.last_name AS lastName,
			    u.email_id AS emailId,
			    u.is_verified AS isVerified,
			    u.phone_no AS phoneNo,
			    u.dob AS dob,
			    u.gender AS gender,
			    u.dietry_pref AS dietryPref,
			    u.user_role AS userRole,
			    u.business_name AS businessName,
			    u.food_license_no AS foodLicenseNo,
			    u.gstin AS gstin,
			    u.latitude AS latitude,
			    u.longitude AS longitude,
			    u.service_radius AS serviceRadius
			FROM `user` u
			WHERE
			    (:userRole IS NULL OR u.user_role = :userRole)
			    AND (
			        :search IS NULL
			        OR LOWER(
			            CONCAT(
			                u.first_name,
			                ' ',
			                COALESCE(u.middle_name, ''),
			                ' ',
			                u.last_name
			            )
			        ) LIKE LOWER(CONCAT('%', :search, '%'))
			        OR LOWER(u.email_id) LIKE LOWER(CONCAT('%', :search, '%'))
			        OR LOWER(u.business_name) LIKE LOWER(CONCAT('%', :search, '%'))
			    )
			ORDER BY u.created_at DESC
			""",

			countQuery = """
					SELECT COUNT(*)
					FROM `user` u
					WHERE
					    (:userRole IS NULL OR u.user_role = :userRole)
					    AND (
					        :search IS NULL
					        OR LOWER(
					            CONCAT(
					                u.first_name,
					                ' ',
					                COALESCE(u.middle_name, ''),
					                ' ',
					                u.last_name
					            )
					        ) LIKE LOWER(CONCAT('%', :search, '%'))
					        OR LOWER(u.email_id) LIKE LOWER(CONCAT('%', :search, '%'))
					        OR LOWER(u.business_name) LIKE LOWER(CONCAT('%', :search, '%'))
					    )
					""",

			nativeQuery = true)
	Page<UserInfo> findUsers(@Param("userRole") String userRole, @Param("search") String search, Pageable pageable);

	// dashboard related

	// get all users of specific roles
	List<UserInfo> findByUserRole(String userRole);

	@Query("SELECT COUNT(u) FROM User u WHERE u.userRole <> :role")
	Long countUsersExcludingRole(@Param("role") String role);

	long countByUserRole(String userRole);

	long countByUserRoleAndActiveTrue(String userRole);

	@Query(value = """
			SELECT

			u.user_id AS userId,

			CONCAT_WS(
			' ',
			u.first_name,
			NULLIF(u.middle_name,''),
			u.last_name
			) AS userName,

			u.email_id AS emailId,

			u.created_at AS createdAt

			FROM user u

			WHERE u.user_role='ROLE_NORMAL_USER'

			ORDER BY u.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)
			FROM user
			WHERE role='ROLE_NORMAL_USER'
			""", nativeQuery = true)
	Page<RecentUserProjection> getRecentUsers(Pageable pageable);

	@Query(value = """
			SELECT

			u.user_id AS providerId,

			u.business_name AS businessName,

			CONCAT_WS(
			' ',
			u.first_name,
			NULLIF(u.middle_name,''),
			u.last_name
			) AS ownerName,

			u.email_id AS emailId,

			u.active AS active,

			u.created_at AS createdAt

			FROM user u

			WHERE u.user_role='ROLE_TIFFIN_PROVIDER'

			ORDER BY u.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)
			FROM user
			WHERE role='ROLE_TIFFIN_PROVIDER'
			""", nativeQuery = true)
	Page<RecentProviderProjection> getRecentProviders(Pageable pageable);

	@Query("""
			SELECT COUNT(u)
			FROM User u
			WHERE u.userRole=:userRole
			AND u.createdAt BETWEEN :start AND :end
			""")
	Long countNewUsersByRole(String userRole, LocalDateTime start, LocalDateTime end);
}
