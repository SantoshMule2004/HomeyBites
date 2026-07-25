package com.homeybites.payloads;

import java.time.LocalDateTime;

public interface UserDetailsProjection {

	Long getUserId();
    String getFirstName();
    String getMiddleName();
    String getLastName();
    String getEmailId();
    Boolean getIsVerified();   // or boolean isVerified()
    String getPhoneNo();
    String getDob();
    String getGender();
    String getDietryPref();
    String getUserRole();

    String getBusinessName();
    String getFoodLicenseNo();
    String getGstin();

    Boolean getActive();

    Double getLatitude();
    Double getLongitude();
    Double getServiceRadius();

    LocalDateTime getCreatedAt();
    
    String getAddressLine();
    String getArea();
}
