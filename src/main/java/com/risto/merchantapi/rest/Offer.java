package com.risto.merchantapi.rest;

public class Offer {
	private long id;
	private String title;
	private String description;
	private String productCode;
	private String merchantName;
	private String merchantCode;
	private String category;
	private int rrp;
	private int price;
	private String currency;
	private int stock;
	private String shippingTime;
	private String imageUrl;
	private String reviews;
	private float averageRating;
	private int ratingCount;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getRrp() {
		return rrp;
	}
	public void setRrp(int rrp) {
		this.rrp = rrp;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getReviews() {
		return reviews;
	}
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
	public float getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public void update(Offer update) {
		this.setAverageRating(update.getAverageRating());
		this.setCategory(update.getCategory());
		this.setCurrency(update.getCurrency());
		this.setDescription(update.getDescription());
		this.setImageUrl(update.getImageUrl());
		this.setMerchantName(update.getMerchantName());
		this.setPrice(update.getPrice());
		this.setProductCode(update.getProductCode());
		this.setRatingCount(update.getRatingCount());
		this.setReviews(update.getReviews());
		this.setRrp(update.getRrp());
		this.setShippingTime(update.getShippingTime());
		this.setStock(update.getStock());
		this.setTitle(update.getTitle());
	}
}
