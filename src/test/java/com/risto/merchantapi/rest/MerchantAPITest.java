package com.risto.merchantapi.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risto.merchantapi.MerchantAPIServer;
import com.risto.merchantapi.rest.MerchantAPI;
import com.risto.merchantapi.rest.Offer;
import com.risto.merchantapi.rest.OfferDatastore;

/**
 * Test Merchant REST API
 * @author risto
 *
 */
public class MerchantAPITest extends AbstractOfferAPITest {

	static OfferDatastore datastore;
	
	/**
	 * Setup REST server
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		MerchantAPIServer.startServer();
		
		api = ResteasyClientBuilder.newClient().target(MerchantAPIServer.BASE_URI + "merchant/api/v1/offers");

		datastore = new OfferDatastore();
		MerchantAPI.setDatastore(datastore);
	}

	
	/**
	 * Shutdown REST server
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		MerchantAPIServer.shutdownServer();
	}


	/**
	 * Create an offer.
	 */
	@Test
	public void testCanCreateOffer() {

		// create offer
		long offerId = createOffer(newOffer());

		Offer offer = datastore.getOfferById(MerchantAPI.testMerchantCode, offerId);
		assertNotNull(offer);
	}
	
	
	/**
	 * Query an offer
	 */
	@Test
	public void testCanQueryOffer() {
		
		// create offer
		long offerId = createOffer(newOffer());

		// get offer by id
		Response resp = api.path("/" + offerId).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		Offer respOffer = resp.readEntity(Offer.class);
		assertEquals(respOffer.getId(), offerId);
		assertEquals(respOffer.getPrice(), 100);
		assertEquals(respOffer.getTitle(), "Test Offer");
	}

	
	/**
	 * Update an offer title.
	 */
	@Test
	public void testCanUpdateOffer() {
		// create offer
		long offerId = createOffer(newOffer());

		// get offer by id
		Response resp = api.path("/" + offerId).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		Offer respOffer = resp.readEntity(Offer.class);
		assertEquals(respOffer.getPrice(), 100);
		assertEquals(respOffer.getTitle(), "Test Offer");

		// update offer
		respOffer.setTitle("Updated Test Offer");
		updateOffer(respOffer);
		
		// get offer by id
		resp = api.path("/" + offerId).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		respOffer = resp.readEntity(Offer.class);
		assertEquals(respOffer.getPrice(), 100);
		assertEquals(respOffer.getTitle(), "Updated Test Offer");		
	}

	
	/**
	 * Delete an offer and query that it doesn't exist afterwards.
	 */
	@Test
	public void testCanDeleteOffer() {
		// create offer
		long offerId = createOffer(newOffer());
		
		// get offer by id
		Response resp = api.path("/" + offerId).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		Offer respOffer = resp.readEntity(Offer.class);
		assertEquals(respOffer.getPrice(), 100);
		assertEquals(respOffer.getTitle(), "Test Offer");

		// delete offer
		deleteOffer(offerId);
		
		// get offer by id
		resp = api.path("/" + offerId).request().buildGet().invoke();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

}
