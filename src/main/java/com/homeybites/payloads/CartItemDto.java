package com.homeybites.payloads;

public class CartItemDto {
    private Long cartItemId;
	private Long cartId;
	private Integer quantity;
	private double priceWhenAdded;
	private double currentPrice;
	private boolean isPriceChanged = false;

	private Long menuItemId;
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

    
    public CartItemDto() {
    }
    
    public CartItemDto(Long cartItemId, Long cartId, Integer quantity, double priceWhenAdded, double currentPrice,
			boolean isPriceChanged, Long menuItemId, String menuName, double price, String description, Long count,
			boolean isActive, String menuType, String imagePublicId, String imageUrl, String format, Long categoryId,
			Long providerId, String businessName) {
		super();
		this.cartItemId = cartItemId;
		this.cartId = cartId;
		this.quantity = quantity;
		this.priceWhenAdded = priceWhenAdded;
		this.currentPrice = currentPrice;
		this.isPriceChanged = isPriceChanged;
		this.menuItemId = menuItemId;
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
	}

	public Long getCartItemId() {
        return cartItemId;
    }
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }
    public Long getCartId() {
        return cartId;
    }
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public double getPriceWhenAdded() {
        return priceWhenAdded;
    }
    public void setPriceWhenAdded(double priceWhenAdded) {
        this.priceWhenAdded = priceWhenAdded;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
    public boolean isPriceChanged() {
        return isPriceChanged;
    }
    public void setPriceChanged(boolean isPriceChanged) {
        this.isPriceChanged = isPriceChanged;
    }
    public Long getMenuItemId() {
        return menuItemId;
    }
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
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

	@Override
	public String toString() {
		return "CartItemDto [cartItemId=" + cartItemId + ", cartId=" + cartId + ", quantity=" + quantity
				+ ", priceWhenAdded=" + priceWhenAdded + ", currentPrice=" + currentPrice + ", isPriceChanged="
				+ isPriceChanged + ", menuItemId=" + menuItemId + ", menuName=" + menuName + ", price=" + price
				+ ", description=" + description + ", count=" + count + ", isActive=" + isActive + ", menuType="
				+ menuType + ", imagePublicId=" + imagePublicId + ", imageUrl=" + imageUrl + ", format=" + format
				+ ", categoryId=" + categoryId + ", providerId=" + providerId + ", businessName=" + businessName + "]";
	}
}
