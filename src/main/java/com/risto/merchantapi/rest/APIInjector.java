package com.risto.merchantapi.rest;

import com.google.inject.AbstractModule;

public class APIInjector extends AbstractModule {

	@Override
	public void configure() {
		bind(OfferDatastore.class).to(OfferDatastoreImpl.class);
	}

}
