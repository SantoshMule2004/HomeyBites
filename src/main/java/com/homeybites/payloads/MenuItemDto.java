package com.homeybites.payloads;

public class MenuItemDto {
    private Long menuId;
    private String menuName;
    private double price;
    private String description;
    private Long count;
    private boolean isActive;
    private String menuType;
    private String imagePublicId;
    private String imageUrl;
    private String format;
    private Long categoryId;

    private Long providerId;
    private String businessName;
    private double latitude;
    private double longitude;
    private double serviceRadius;

    public MenuItemDto() {
    }

    public MenuItemDto(Long menuId, String menuName, double price, String description, Long count, boolean isActive,
            String menuType, String imagePublicId, String imageUrl, String format, Long categoryId, Long providerId,
            String businessName, double latitude, double longitude, double serviceRadius) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.count = count;
        this.isActive = isActive;
        this.menuType = menuType;
        this.imagePublicId = imagePublicId;
        this.imageUrl = imageUrl;
        this.format = format;
        this.categoryId = categoryId;
        this.providerId = providerId;
        this.businessName = businessName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.serviceRadius = serviceRadius;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getImagePublicId() {
        return imagePublicId;
    }

    public void setImagePublicId(String imagePublicId) {
        this.imagePublicId = imagePublicId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(double serviceRadius) {
        this.serviceRadius = serviceRadius;
    }

}
