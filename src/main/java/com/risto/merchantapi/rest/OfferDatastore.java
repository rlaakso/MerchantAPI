package com.risto.merchantapi.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Dummy datastore based on HashMap.
 * TODO implement merchantCode checking - is this the correct place to enforce?
 * 
 * @author risto
 *
 */
public class OfferDatastore {

	private long nextId = 1;
	private Map<Long, Offer> db = new HashMap<Long, Offer>();
	
	/**
	 * Check if Offer exists
	 * @param merchantCode
	 * @param id
	 * @return true/false
	 */
	public boolean offerExists(String merchantCode, long id) {
		return db.containsKey(id);
	}
	
	/**
	 * Get Offer by id
	 * @param merchantCode
	 * @param id
	 * @return offer or null if offer is not found
	 */
	public Offer getOfferById(String merchantCode, long id) {
		return db.get(id);
	}
	
	/**
	 * Create new Offer
	 * @param merchantCode
	 * @return offer
	 */
	public Offer createOffer(String merchantCode) {
		Offer offer = new Offer();
		offer.setId(nextId++);
		offer.setMerchantCode(merchantCode);
		db.put(offer.getId(), offer);
		return offer;
	}
	
	/**
	 * Delete Offer by id
	 * @param merchantCode
	 * @param id
	 * @return Offer deleted or null if offer was not in database
	 */
	public Offer deleteOfferById(String merchantCode, long id) {
		return db.remove(id);
	}
	
	/**
	 * Update OFfer
	 * @param merchantCode
	 * @param offer
	 */
	public void updateOffer(String merchantCode, Offer offer) {
		db.put(offer.getId(), offer);
	}
	
	/**
	 * Get all Offers
	 * @param merchantCode
	 * @return
	 */
	public List<Offer> getAllOffers(String merchantCode) {
		// TODO use database
		List<Offer> ret = new ArrayList<Offer>(db.values());
		ret.sort(new Comparator<Offer>() {
			@Override
			public int compare(Offer o1, Offer o2) {
				String o1Title = o1.getTitle();
				String o2Title = o2.getTitle();
				if (o1Title != null && o2Title != null) {
					return o1Title.compareTo(o2Title);
				}
				return 1; // TODO .. but doesn't really matter
			}
		});
		return ret;
	}
}
