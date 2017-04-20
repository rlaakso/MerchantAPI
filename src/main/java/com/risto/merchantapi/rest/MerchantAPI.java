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

@Path("/merchant/api/v1")
public class MerchantAPI {

	// TODO use dependency injection, remove static,  and add a proper database
	protected static OfferDatastore datastore = new OfferDatastore();

	// TODO get from authentication token
	protected static final String testMerchantCode = "TESTMERCHANT";
	
	// TODO dependency injection, remove static
	protected static void setDatastore(OfferDatastore datastore) {
		MerchantAPI.datastore = datastore;
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
    public Offer getOffer(@PathParam("id") long id) {
		Offer offer = datastore.getOfferById(testMerchantCode, id);
    	return offer;
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
        return Response.status(Response.Status.OK).build();
    }

}

