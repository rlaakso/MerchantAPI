package com.risto.merchantapi.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Singleton;

/**
 * 
 * Dummy datastore based on HashMap.
 * TODO implement merchantCode checking - is this the correct place to enforce?
 * 
 * @author risto
 *
 */
@Singleton
public class OfferDatastoreImpl implements OfferDatastore {

	private long nextId = 1;
	private Map<Long, Offer> db = new HashMap<Long, Offer>();
	
	/* (non-Javadoc)
	 * @see com.risto.merchantapi.rest.IOfferDatastore#offerExists(java.lang.String, long)
	 */
	@Override
	public boolean offerExists(String merchantCode, long id) {
		return db.containsKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.risto.merchantapi.rest.IOfferDatastore#getOfferById(java.lang.String, long)
	 */
	@Override
	public Offer getOfferById(String merchantCode, long id) {
		return db.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.risto.merchantapi.rest.IOfferDatastore#createOffer(java.lang.String)
	 */
	@Override
	public Offer createOffer(String merchantCode) {
		Offer offer = new Offer();
		offer.setId(nextId++);
		offer.setMerchantCode(merchantCode);
		db.put(offer.getId(), offer);
		return offer;
	}
	
	/* (non-Javadoc)
	 * @see com.risto.merchantapi.rest.IOfferDatastore#deleteOfferById(java.lang.String, long)
	 */
	@Override
	public Offer deleteOfferById(String merchantCode, long id) {
		return db.remove(id);
	}
	
	/* (non-Javadoc)
	 * @see com.risto.merchantapi.rest.IOfferDatastore#updateOffer(java.lang.String, com.risto.merchantapi.rest.Offer)
	 */
	@Override
	public void updateOffer(String merchantCode, Offer offer) {
		db.put(offer.getId(), offer);
	}
	
	/* (non-Javadoc)
	 * @see com.risto.merchantapi.rest.IOfferDatastore#getAllOffers(java.lang.String)
	 */
	@Override
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
	
	
	@Override
	public void clear() {
		db.clear();
	}
}
