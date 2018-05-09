package com.timer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.json.simple.JSONObject;

 @SuppressWarnings("deprecation")
 @Provider
 @ServerInterceptor
 public class ResponseValidator implements PreProcessInterceptor, AcceptedByMethod {

  @Override
   public boolean accept(Class declaring, Method method) {
    String methodName= method.getName();
      if(methodName.equals("update"))
         return true;
      else return false;
   }

  @Override
   public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker method)
      throws Failure, WebApplicationException {
       ServerResponse response = null;
         try {
        	 if(request.getInputStream().available()==0)
        	 	{
        		 JSONObject result= new JSONObject();
        		 result.put("Success",false);
        		 result.put("message", "request body empty");
        		 response = new ServerResponse(result,400,new Headers<Object>());
        		 return response;
        	 	}

         		} catch (IOException e) {

         			e.printStackTrace();
         		}

         return response;


    }
}
