package com.timer;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import org.json.simple.JSONObject;


import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import static com.timer.ConfigureObjectify.ofy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Path("/user") 
public class HelloRestEasy {
	 private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	 public static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");

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
	          result.put("message","Your timer is started");
	          result.put("entryid",t.getId());
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
	
	@PUT
	@Path("/clockout/{userid}/{entryid}")
	@Produces("application/json") 
	public JSONObject clockOut(@PathParam("userid") String id,@PathParam("entryid") String entryid,@Context HttpServletRequest request,@Context HttpServletResponse response)
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
	          Long outtime=date.getTime();
	          Long entryids=Long.parseLong(entryid);
	         
	          Timer t=ofy().load().type(Timer.class).id(entryids).now(); 
	          
	          t.setOutTime(outtime); 
	          t.setCompleted(true);  
	          ofy().save().entity(t).now();  
	           
	          result.put("success",true);
	          result.put("message","Your timer is stopped");
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
}
   

