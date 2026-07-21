package com.homeybites.payloads;

public interface BusinessDetailsProjection {

    String getBusinessName();
    String getFoodLicenseNo();
    String getGSTIN();

    Double getLatitude();
    Double getLongitude();
    Double getServiceRadius();

    // Address Details
    String getAddressLine();
    String getArea();
}
