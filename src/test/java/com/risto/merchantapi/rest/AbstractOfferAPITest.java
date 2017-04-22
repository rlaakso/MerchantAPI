package com.risto.merchantapi.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.risto.merchantapi.rest.Offer;

/**
 * Abstract base class for offer related API tests
 * 
 * @author risto
 *
 */
public abstract class AbstractOfferAPITest {
	
	protected static WebTarget merchantApi;
	
	/**
	 * Create a new offer instance
	 * @return
	 */
	protected Offer newOffer(String title) {
		Offer testOffer = new Offer();
		testOffer.setTitle(title);
		testOffer.setPrice(100);
		return testOffer;
	}

	/**
	 * Create a new offer instance
	 * @return
	 */
	protected Offer newOffer(String title, String merchantCode) {
		Offer testOffer = new Offer();
		testOffer.setTitle(title);
		testOffer.setMerchantCode(merchantCode);
		testOffer.setPrice(100);
		return testOffer;
	}

	/**
	 * Create a new offer instance
	 * @return
	 */
	protected Offer newOffer() {
		return newOffer("Test Offer");
	}

	/**
	 * Create an offer with the REST API
	 * @param testOffer
	 * @return
	 */
	protected long createOffer(Offer testOffer) {
		
		Entity<Offer> testEnt = Entity.json(testOffer);
		
		// test creation
		Response resp = merchantApi.request().buildPost(testEnt).invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		
		// get offer id from response
		Offer respOffer = resp.readEntity(Offer.class);
		assertTrue(respOffer.getId() > 0);

		return respOffer.getId();
	}

	/**
	 * Update an offer with the REST API
	 * @param testOffer
	 */
	protected void updateOffer(Offer testOffer) {
		
		Entity<Offer> testEnt = Entity.json(testOffer);
		
		// test update
		Response resp = merchantApi.path("/" + testOffer.getId()).request().put(testEnt);
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());		
	}

	/**
	 * Delete an offer with the REST API
	 * @param offerId
	 */
	protected void deleteOffer(long offerId) {
		// test deletion
		Response resp = merchantApi.path("/" + offerId).request().buildDelete().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());	
	}
	
}
