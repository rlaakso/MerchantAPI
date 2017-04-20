package com.risto.merchantapi.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * API to list and purchase offers
 * @author risto
 *
 */
@Path("/query/api/v1")
public class QueryAPI {

	// TODO use dependency injection, remove static,  and add a proper database
	protected static OfferDatastore datastore = new OfferDatastore();

	// TODO get from authentication token
	protected static final String testMerchantCode = "TESTMERCHANT";
	
	// TODO dependency injection, remove static
	protected static void setDatastore(OfferDatastore datastore) {
		MerchantAPI.datastore = datastore;
	}
	
	/**
	 * Get Offer by id
	 * @param id offer id
	 * @return offer as json
	 */
	@Path("/offers/{id}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Offer getOffer(@PathParam("id") long id) {
    	Offer offer = datastore.getOfferById(testMerchantCode, id);
        return offer;
    }
    

	/**
	 * List all Offers
	 * @param merchant
	 * @param offset
	 * @param limit
	 * @param search
	 * @return
	 */
	@Path("/offers")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> listOffers(@QueryParam("merchant") String merchant, 
    		@QueryParam("offset") long offset, 
    		@QueryParam("limit") long limit, 
    		@QueryParam("search") String search) {
		return datastore.getAllOffers(testMerchantCode);
    }
    

	/**
	 * Purchase Offer
	 * @param id
	 * @return purchase link object
	 */
	@Path("/offers/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PurchaseLink purchaseOffer(@PathParam("id") long id) {
    	// TODO complete
    	PurchaseLink link = new PurchaseLink();
    	
    	Offer offer = datastore.getOfferById(testMerchantCode, id);
    	// orderCode = offer.generateOrder()
    	String orderCode = "ORDER-CODE-001";
    	link.setOrderCode(orderCode);
    	
    	link.setPaymentPage("https://paymentpage.com/purchase/api/v1/purchaseOffer");
        return link;
    }

}

