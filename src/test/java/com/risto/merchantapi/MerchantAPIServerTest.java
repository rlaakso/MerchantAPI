package com.risto.merchantapi;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import junit.framework.TestCase;

public class MerchantAPIServerTest extends TestCase {

	public void testServerCanStart() {
		MerchantAPIServer.startServer();

		WebTarget api = ResteasyClientBuilder.newClient().target(MerchantAPIServer.BASE_URI + "merchant/api/v1/status");
		Response resp = api.request().buildGet().invoke();
		assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
		assertEquals("OK", resp.readEntity(String.class));
		
		MerchantAPIServer.shutdownServer();
	}
}
