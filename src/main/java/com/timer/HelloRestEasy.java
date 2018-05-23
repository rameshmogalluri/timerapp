package com.timer;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static com.timer.ConfigureObjectify.ofy;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;


@Path("/user") 
public class HelloRestEasy {
	
	 public static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
	 public static SimpleDateFormat day=new SimpleDateFormat("EEE MMM dd");
	 public static SimpleDateFormat time=new SimpleDateFormat("hh:mm:ss a");
	 TimeZone timezone = TimeZone.getDefault();
	
	// private static Logger logger = Logger.getLogger("com.timer.HelloRestEasy");

	@SuppressWarnings("unchecked")
	@POST
    @Path("/create")  
	@Consumes("application/json")
	@Produces("application/json") 
    public JSONObject create(Contact user,@Context HttpServletRequest request,@Context HttpServletResponse response) { 
		
		user.setActive(true);
		JSONObject result = new JSONObject();
		Contact results = ofy().load().type(Contact.class).filter("email",user.getEmail()).first().now();
		if (results!=null) {	
		result.put("Success", false);
		result.put("message", "Email already registered");
		response.setStatus(406);
		response.setContentType("application/json"); 
		return result;
       }
		else
		{
			ofy().save().entity(user).now();
			HttpSession session=request.getSession();
			session.setAttribute("user", user); 
			result.put("Success", true);
			result.put("message", "Successfully registred");
			response.setStatus(201);
			response.setContentType("application/json"); 
			return result; 
		}
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Consumes("application/json")  
	@Produces("application/json")
	public JSONObject login(Contact login,@Context HttpServletResponse responses,@Context HttpServletRequest request)
	{
		JSONObject response = new JSONObject();
		Contact result=ofy().load().type(Contact.class).filter("email",login.getEmail()).first().now();  
		if(result!=null)
		{
			 
			if(login.getPassword().equals(result.getPassword()) && result.getActive()) 
			{
				response.put("success",true);
				response.put("message","login success");
				HttpSession session=request.getSession();
				session.setAttribute("user",result); 
				responses.setStatus(HttpServletResponse.SC_ACCEPTED);
				responses.setContentType("application/json"); 
				return response;
			}
			else
			{
				response.put("success",false);
				response.put("message","Invalid credetails");
				responses.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				responses.setContentType("application/json"); 
				return response;
			}
		}
		else
		{
			response.put("success",false);
			response.put("message","Invalid credetails");
			responses.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			responses.setContentType("application/json"); 
			return response;
		}
		
	}
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/signinwithgoogle")
	@Consumes("application/x-www-form-urlencoded")   
	@Produces("application/json")
	public JSONObject googlesignin(String idtoken,@Context HttpServletRequest request,@Context HttpServletResponse response) throws GeneralSecurityException, IOException
	{
		JSONObject result =new JSONObject();
		
						  
		
		return result;	
	}
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/update/{userid}")
	@Consumes("application/json")
	@Produces("application/json") 
	public JSONObject update(Contact updatedeatils,@PathParam("userid")String id,@Context HttpServletRequest request,@Context HttpServletResponse response)
	{
		String name=(String) updatedeatils.getName();
		String mobileNumber=(String)updatedeatils.getmobileNumber();
		String address=(String)updatedeatils.getAddress();
		
		HttpSession session=request.getSession();
        Contact user=(Contact)session.getAttribute("user");
        JSONObject responsejson=new JSONObject();
        if(session.getAttribute("user")!=null)
        {
        String password=user.getPassword();
        String email=user.getEmail();
        
        String accId=""+user.getId();
        if(id.equals(accId))
        {
        Contact results = ofy().load().type(Contact.class).filter("email",user.getEmail()).first().now();
       
        	if(results.getName().equals(name) && results.getmobileNumber().equals(mobileNumber) && results.getAddress().equals(address))
        	{
        		responsejson.put("success", false);
        		responsejson.put("message","No changes to modify");
        		response.setContentType("application/json");
        		response.setStatus(304); 
        		return responsejson;
        	}
        	else{
        		 
        		results.setName(name);  
        		results.setmobileNumber(mobileNumber); 
        		results.setAddress(address);
        		results.setEmail(email); 
        		results.setPassword(password);  
        		ofy().save().entity(results).now();  
        		responsejson.put("success", true);
        		responsejson.put("message","successfully updated deatils");
        		responsejson.put("name",name);
        		responsejson.put("phoneno",mobileNumber);
        		responsejson.put("address",address);
        		response.setContentType("application/json");
        		return responsejson;
        	}
        
      }
        else
        {
        	responsejson.put("success",false);
    		responsejson.put("message","Invalid user");
    		response.setContentType("application/json");
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	return responsejson;
        }
      }
        else 
        {
        	responsejson.put("success",false);
    		responsejson.put("message","Please login");
    		response.setContentType("application/json");
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		return responsejson;
        }
	}
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/deactivate/{userid}")
	@Produces("application/json")
	public JSONObject deactivate(@Context HttpServletRequest request,@PathParam("userid")String id,@Context HttpServletResponse response)
	{
		 
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		Contact user=(Contact)session.getAttribute("user");
		
		if(session.getAttribute("user")!=null)
		{
		 String accId=""+user.getId();
		 if(id.equals(accId))
		 {
		Contact c=ofy().load().type(Contact.class).filter("email",user.getEmail()).first().now();
		c.setActive(false);  
		ofy().save().entity(c).now();
		 session=request.getSession(false); 
      	session.invalidate(); 
		result.put("success",true);
		result.put("message","Successfully deactivated your account");
		return result;
		 }
		 else
		 {
			 result.put("success",false);
			 result.put("message","Invalid user");
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			 return result;	 
		 }
	  }
	else {
		result.put("success",false);
		 result.put("message","Please login"); 
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 return result;	
	}
	}
	@SuppressWarnings("unchecked")
	@GET
	@Path("/logout")
	@Produces("application/json")
	public JSONObject logout(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException
	{
	JSONObject result = new JSONObject();
	HttpSession session = request.getSession(false);
	if(session!=null)
	{ 
		session.invalidate();
	result.put("Success", true);
	result.put("message", "Successfully logged out");
	response.setContentType("application/json;charset=UTF-8");
	response.setStatus(HttpServletResponse.SC_ACCEPTED);
	return result;
	}
	else
	{
	   result.put("Success", false);
	   result.put("message", "you  are not Logged In");
	   response.setContentType("application/json;charset=UTF-8");
	  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	return result;
	}
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/clockin/{userid}")
	@Produces("application/json") 
	public JSONObject clockIn(@PathParam("userid") String id,@Context HttpServletRequest request,@Context HttpServletResponse response)
	{
		JSONObject result=new JSONObject();
	    HttpSession session=request.getSession();
	    Contact loginuser=(Contact)session.getAttribute("user");
	    if(session.getAttribute("user")!=null)
	    {
	    	String accId=""+loginuser.getId();
	    	if(id.equals(accId))
	    	{
	    		 
	          Date date = new Date(); 
	          
	          Long intime=date.getTime();
	          
	          Timer t=new Timer(loginuser.getId(),intime); 
	          ofy().save().entity(t).now(); 
	          
	          result.put("success",true);
	          result.put("runningentry",t);
	          response.setContentType("application/json;charset=UTF-8");
	          return result; 
	    	}
	    	else {
	    		
	    		 result.put("success",false);
				 result.put("message","Invalid user");
				 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				 return result;	 
	    	}
	    }
	    else {
	    	result.put("success",false);
			 result.put("message","Please login"); 
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	return result;
	    }
	    
	}   
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/clockout/{entryid}")
	@Produces("application/json") 
	public JSONObject clockOut(@PathParam("entryid") String entryid,@Context HttpServletRequest request,@Context HttpServletResponse response)
	{
		JSONObject result=new JSONObject();
	   
	   
	    	
	          Date date = new Date(); 
	          Long outtime=date.getTime();
	          Long entryids=Long.parseLong(entryid);
	         
	         
	          
	          Timer t=ofy().load().type(Timer.class).id(entryids).now();
	           
	          
	          t.setOutTime(outtime); 
	          t.setCompleted(true);  
	          ofy().save().entity(t).now();  
	           
	         
	          
	          result.put("success",true);
	          result.put("clockoutentry",t); 
	          response.setContentType("application/json;charset=UTF-8");
	          return result; 
	    
	} 
	
   @SuppressWarnings("unchecked")
   @GET
   @Path("/timerentries/{userid}") 
   @Produces("application/json")
   public Response timerentries(@PathParam("userid") String userid) 
   {
	   JSONArray timeentrylist=new JSONArray();
	   JSONObject timeentry = new JSONObject();
	   
	
	    
	   Calendar c = Calendar.getInstance();
	    c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    Date sun=c.getTime();
	    
	     
	     Date d=new Date();
	     TimeZone tz=TimeZone.getDefault();
	     day.setTimeZone(tz); 
	     String today=day.format(d);
List<Timer> timerInfoList = ofy().load().type(Timer.class).filter("userId", Long.parseLong(userid)).filter("inTime >",sun.getTime()).filter("delete",false).list(); 
	   
	   for(Timer t:timerInfoList)
	   {
		   timeentrylist.add(t);   
	   }  
	   timeentry.put("success",true);
	   timeentry.put("currentdate",d.getTime()); 
	   timeentry.put("todaydate",today); 
	   timeentry.put("timeentry",timeentrylist);
	   
	   return Response.status(200).entity(timeentry).build(); 
      }
   @SuppressWarnings({ "unchecked", "unused" })
   @PUT
   @Produces("application/json")
   @Path("/timerentry/delete/{entryid}") 
	public JSONObject deleteentry(@PathParam("entryid") String entryid){  	
	   JSONObject result=new JSONObject();
	   
	   Long entryids=Long.parseLong(entryid);
       Timer t=ofy().load().type(Timer.class).id(entryids).now();
       t.setDelete(true);  
       
       if(t!=null)
       {
    	   ofy().save().entity(t).now(); 
       result.put("success", true); 
       result.put("message","Successfully deleted");
       }  
       else
       {
    	   result.put("success",false); 
    	   result.put("message","Entry id not existed");
       }
	   return result;
	 }
 }


   

