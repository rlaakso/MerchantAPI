package com.risto.merchantapi.rest;

import java.util.List;

public interface OfferDatastore {

	/**
	 * Check if Offer exists
	 * @param merchantCode
	 * @param id
	 * @return true/false
	 */
	boolean offerExists(String merchantCode, long id);

	/**
	 * Get Offer by id
	 * @param merchantCode
	 * @param id
	 * @return offer or null if offer is not found
	 */
	Offer getOfferById(String merchantCode, long id);

	/**
	 * Create new Offer
	 * @param merchantCode
	 * @return offer
	 */
	Offer createOffer(String merchantCode);

	/**
	 * Delete Offer by id
	 * @param merchantCode
	 * @param id
	 * @return Offer deleted or null if offer was not in database
	 */
	Offer deleteOfferById(String merchantCode, long id);

	/**
	 * Update OFfer
	 * @param merchantCode
	 * @param offer
	 */
	void updateOffer(String merchantCode, Offer offer);

	/**
	 * Get all Offers
	 * @param merchantCode
	 * @return
	 */
	List<Offer> getAllOffers(String merchantCode);

	
	/**
	 * Empty datastore TODO use mock datastore instead for test cases
	 */
	void clear();

}