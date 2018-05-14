package com.timer;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class ConfigureObjectify {
	static {
		factory().register(Contact.class);
		
		}

		
		public static Objectify ofy() {
			
		return ObjectifyService.ofy();
		
		}

		public static ObjectifyFactory factory() {
			
		return ObjectifyService.factory();
		
		}
}
