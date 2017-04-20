package com.risto.merchantapi;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class MerchantAPIServer {
	
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/gw/";

    public static HttpServer server;
    
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        final ResourceConfig rc = new ResourceConfig().packages("com.risto.merchantapi.rest");
//        final ResourceConfig rc = new ResourceConfig().packages("com.example.rest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        return server;
    }
    
    public static void shutdownServer() {
    	server.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        MerchantAPIServer.startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        MerchantAPIServer.shutdownServer();
    }
}