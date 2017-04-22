package com.risto.merchantapi.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceInjector {

	private static Injector injector;
	
	public static Injector getInjector() {
		synchronized (GuiceInjector.class) {
			if (injector == null) {
				injector = Guice.createInjector(new APIInjector());
			}
		}
		return injector;
	}
}
