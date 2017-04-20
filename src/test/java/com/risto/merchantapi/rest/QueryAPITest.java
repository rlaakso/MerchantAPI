package com.risto.merchantapi.rest;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risto.merchantapi.MerchantAPIServer;
import com.risto.merchantapi.rest.Offer;

/**
 * Test Query API
 * @author risto
 *
 */
public class QueryAPITest extends AbstractOfferAPITest {

	static OfferDatastore datastore;

	@BeforeClass
	public static void setUp() throws Exception {
		MerchantAPIServer.startServer();	
		
		api = ResteasyClientBuilder.newClient().target(MerchantAPIServer.BASE_URI + "query/api/v1/offers");
		
		datastore = new OfferDatastore();
		MerchantAPI.setDatastore(datastore);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		MerchantAPIServer.shutdownServer();
	}

	/**
	 * Query offer by id
	 */
	@Test
	public void testCanQueryOfferById() {
		
		// create offer
		long offerId = createOffer(newOffer());

		Response resp = api.request().build("/" + offerId).invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		Offer respOffer = resp.readEntity(Offer.class);
		assertEquals(respOffer.getPrice(), 100);
		assertEquals(respOffer.getTitle(), "Test Offer");
	}

	/**
	 * List offers
	 */
	@Test
	public void testCanListOffers() {

		// create offers
		createOffer(newOffer("Offer 1"));
		createOffer(newOffer("Offer 2"));
		createOffer(newOffer("Offer 3"));

		Response resp = api.request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		List<Offer> offers = resp.readEntity(List.class); // TODO check this
		assertEquals(offers.size(), 3);
		assertEquals(offers.get(1).getTitle(), "Offer 1");
		assertEquals(offers.get(1).getTitle(), "Offer 2");
		assertEquals(offers.get(1).getTitle(), "Offer 3");
	}

	/**
	 * List offers with pagination
	 */
	@Test
	public void testOfferPagination() {
		// create offers
		createOffer(newOffer("Offer 1"));
		createOffer(newOffer("Offer 2"));
		createOffer(newOffer("Offer 3"));

		Response resp = api.queryParam("offset", 1).queryParam("limit", 1).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());

		List<Offer> offers = resp.readEntity(List.class); // TODO check this
		assertEquals(offers.size(), 1);
		assertEquals(offers.get(1).getTitle(), "Offer 2");
	}

	/**
	 * List offers with merchant filter
	 */
	@Test
	public void testListOffersByMerchant() {
		// create offers
		createOffer(newOffer("Offer 1", "MERCHANT1"));
		createOffer(newOffer("Offer 2", "MERCHANT2"));
		createOffer(newOffer("Offer 3", "MERCHANT1"));

		Response resp = api.queryParam("merchant", "MERCHANT2").request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());

		List<Offer> offers = resp.readEntity(List.class); // TODO check this
		assertEquals(offers.size(), 1);
		assertEquals(offers.get(1).getTitle(), "Offer 2");
	}
	
	/**
	 * Free-text search of offers
	 */
	@Test
	public void testOfferSearch() {
		fail("Not yet implemented");
	}

	/**
	 * Purchase an offer
	 */
	@Test
	public void testCanPurchaseOffer() {
		fail("Not yet implemented");
	}

}
