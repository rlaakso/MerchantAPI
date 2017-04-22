package com.risto.merchantapi.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/merchant/api/v1")
public class MerchantAPI {

	// TODO add a proper database
	protected OfferDatastore datastore;

	private Logger logger = LoggerFactory.getLogger("com.risto.merchantapi.rest.MerchantAPI");
    
	
	// TODO get from authentication token
	protected static final String testMerchantCode = "TESTMERCHANT";
	
	public MerchantAPI() {
		datastore = GuiceInjector.getInjector().getInstance(OfferDatastore.class);
	}
	
	
	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getStatus() {
		// TODO do something more meaningful
	    return "OK";
	}
	
	
	/**
	 * Get an offer by id
	 * @param id
	 * @return
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
	 * Create a new offer
	 * @return
	 */
	@Path("/offers")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Offer createOffer(Offer offerData) {
		Offer offer = datastore.createOffer(testMerchantCode);
		offer.update(offerData);
		datastore.updateOffer(testMerchantCode, offer);
        logger.info("Offer " + offer.getId() + " created.");
		return offer;
    }
    
	
    /**
     * Update offer
     * @param id
     * @param offerData
     * @return 
     */
    @Path("/offers/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOffer(@PathParam("id") long id, Offer offerData) {
    	if (!datastore.offerExists(testMerchantCode, id)) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Offer not found").build();
    	}
    	
    	Offer offer = datastore.getOfferById(testMerchantCode, id);
        offer.update(offerData);
        datastore.updateOffer(testMerchantCode, offer);
        logger.info("Offer " + offer.getId() + " updated.");
        return Response.status(Response.Status.OK).build();
    }

    
    /**
     * Delete an offer
     * @param id
     * @return 
     */
    @Path("/offers/{id}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteOffer(@PathParam("id") long id) {
    	if (!datastore.offerExists(testMerchantCode, id)) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Offer not found").build();
    	}

        datastore.deleteOfferById(testMerchantCode, id);
        logger.info("Offer " + id + " deleted.");
        return Response.status(Response.Status.OK).build();
    }

}

