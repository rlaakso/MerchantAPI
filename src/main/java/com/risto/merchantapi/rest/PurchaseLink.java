package com.risto.merchantapi.rest;

/**
 * Payment page redirect object
 * @author risto
 *
 */
public class PurchaseLink {

	private String paymentPage;
	private String orderCode;
	
	public String getPaymentPage() {
		return paymentPage;
	}
	public void setPaymentPage(String paymentPage) {
		this.paymentPage = paymentPage;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
}
