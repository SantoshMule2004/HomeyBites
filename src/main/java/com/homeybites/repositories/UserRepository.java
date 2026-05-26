package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.User;
import com.homeybites.payloads.UserInfo;

public interface UserRepository extends JpaRepository<User, Integer> {

	// find user by email id
	Optional<User> findByEmailId(String emailId);

	// is user present by email id
	boolean existsByEmailId(String username);

	// get all users of specific roles
	List<UserInfo> findByUserRole(String userRole);

//	List<User> findByAddressIn(List<Address> address);

	@Query("SELECT COUNT(u) FROM User u WHERE u.userRole <> :role")
	Integer getAllUserCount(@Param("role") String role);

	@Query("SELECT COUNT(u) FROM User u WHERE u.userRole =:role")
	Integer getUserCount(@Param("role") String role);

	@Query(value = """
			SELECT p.userId FROM User p
			WHERE (
			    6371 * acos(
			        cos(radians(:userLat)) * cos(radians(p.latitude)) * cos(radians(p.longitude) - radians(:userLng)) +
			        sin(radians(:userLat)) * sin(radians(p.latitude))
			    )
			) <= p.serviceRadius
			""", nativeQuery = true)
	List<Integer> findProvidersDeliveringToLocation(@Param("userLat") double userLat,
			@Param("userLng") double userLng);
	
	
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
}
