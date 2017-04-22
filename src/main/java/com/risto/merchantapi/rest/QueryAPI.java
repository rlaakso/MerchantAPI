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
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;

/**
 * API to list and purchase offers
 * @author risto
 *
 */
@Path("/query/api/v1")
public class QueryAPI {

	// TODO add a proper database
	protected OfferDatastore datastore;

	private Logger logger = LoggerFactory.getLogger("com.risto.merchantapi.rest.QueryAPI");

	
	// TODO get from authentication token
	protected static final String testMerchantCode = "TESTMERCHANT";
	
	public QueryAPI() {
		datastore = GuiceInjector.getInjector().getInstance(OfferDatastore.class);
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
    public Response getOffer(@PathParam("id") long id) {
    	Offer offer = datastore.getOfferById(testMerchantCode, id);
    	if (offer == null) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Offer not found").build();
    	}
        return Response.ok(offer, MediaType.APPLICATION_JSON).build();
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
    		@QueryParam("offset") int offset, 
    		@QueryParam("limit") int limit, 
    		@QueryParam("search") String search) {
		List<Offer> lst = datastore.getAllOffers(testMerchantCode);
		if (offset > 0 || limit > 0) {
			lst = lst.subList(offset, offset+limit);
		}
		// TODO add Java8 filter to merchant filtering, or with database
		// TODO implement search with proper database
		return lst;
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

        logger.info("Offer " + id + " purchased.");
    	return link;
    }

}

