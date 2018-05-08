package com.timer;

import java.util.HashSet;

import javax.ws.rs.core.Application;

public class RestEasyConfigure extends Application{
	 
	private HashSet<Object> singletons = new HashSet<Object>();
	
	public RestEasyConfigure()
	{
		singletons.add(new HelloRestEasy());
		singletons.add(new Index());
		
	}
	
	public HashSet<Object> getSingletons() {
		return singletons;
	}

}
