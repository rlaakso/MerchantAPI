package com.risto.merchantapi.rest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
	protected static WebTarget queryApi;

	@BeforeClass
	public static void setUpClass() throws Exception {
		MerchantAPIServer.startServer();	
	}

	@AfterClass
	public static void tearDown() throws Exception {
		MerchantAPIServer.shutdownServer();
	}
	
	
	@Before
	public void setup() throws Exception {
		merchantApi = ResteasyClientBuilder.newClient().target(MerchantAPIServer.BASE_URI + "merchant/api/v1/offers");
		queryApi = ResteasyClientBuilder.newClient().target(MerchantAPIServer.BASE_URI + "query/api/v1/offers");

		datastore = GuiceInjector.getInjector().getInstance(OfferDatastore.class);
	}
	

	/**
	 * Query offer by id
	 */
	@Test
	public void testCanQueryOfferById() {
		
		// create offer
		long offerId = createOffer(newOffer());

		Offer offer = datastore.getOfferById(MerchantAPI.testMerchantCode, offerId);
		assertNotNull(offer);

		Response resp = queryApi.path("/" + offerId).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		Offer respOffer = resp.readEntity(Offer.class);
		assertEquals(respOffer.getId(), offerId);
		assertEquals(respOffer.getPrice(), 100);
		assertEquals(respOffer.getTitle(), "Test Offer");
	}

	/**
	 * List offers
	 */
	@Test
	public void testCanListOffers() {

		datastore.clear();
		
		// create offers
		createOffer(newOffer("Offer 1"));
		createOffer(newOffer("Offer 2"));
		createOffer(newOffer("Offer 3"));

		Response resp = queryApi.request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		
		List<LinkedHashMap<String, String>> offers = resp.readEntity(List.class); // TODO check this, how to convert from LinkedHashMap to Offer?
		assertEquals(offers.size(), 3);
		
		Set<String> titles = new HashSet<>(); // TODO fixme list return as sorted arraylist, not hashmaps
		titles.add(offers.get(0).get("title"));
		titles.add(offers.get(1).get("title"));
		titles.add(offers.get(2).get("title"));
		
		assertTrue(titles.contains("Offer 1"));
		assertTrue(titles.contains("Offer 2"));
		assertTrue(titles.contains("Offer 3"));
	}

	/**
	 * List offers with pagination
	 */
	@Test
	public void testOfferPagination() {
		
		datastore.clear();

		// create offers
		createOffer(newOffer("Offer 1"));
		createOffer(newOffer("Offer 2"));
		createOffer(newOffer("Offer 3"));

		Response resp = queryApi.queryParam("offset", 1).queryParam("limit", 1).request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());

		List<LinkedHashMap<String, String>> offers = resp.readEntity(List.class); // TODO check this
		assertEquals(offers.size(), 1);
		assertEquals(offers.get(0).get("title"), "Offer 2");
	}

	/**
	 * List offers with merchant filter
	 */
	@Test
	public void testListOffersByMerchant() {
		
		datastore.clear();

		// create offers
		createOffer(newOffer("Offer 1", "MERCHANT1"));
		createOffer(newOffer("Offer 2", "MERCHANT2"));
		createOffer(newOffer("Offer 3", "MERCHANT1"));

		Response resp = queryApi.queryParam("merchant", "MERCHANT2").request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());

		List<LinkedHashMap<String, String>> offers = resp.readEntity(List.class); // TODO check this
		assertEquals(offers.size(), 1);
		assertEquals(offers.get(0).get("title"), "Offer 2");
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
		// create offer
		long offerId = createOffer(newOffer());

		Response resp = queryApi.path("/" + offerId).request().buildPut(Entity.json("purchase")).invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());

		PurchaseLink plink = resp.readEntity(PurchaseLink.class);
		assertEquals(plink.getOrderCode(), "ORDER-CODE-001");
		assertEquals(plink.getPaymentPage(), "https://paymentpage.com/purchase/api/v1/purchaseOffer");

		// TODO fixme test payment completion
	}

}
