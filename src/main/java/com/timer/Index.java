package com.timer;
import static com.timer.ConfigureObjectify.ofy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.Path;

@Path("/")
public class Index {
	private static Logger logger = Logger.getLogger("com.timer.Index");
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void index(@Context HttpServletRequest request, @Context HttpServletResponse response) throws URISyntaxException, IOException, ServletException 
	{
	request.getRequestDispatcher("index.jsp").forward(request, response);

	}
	@POST
	@Path("/imageupload")   
	public void upload(@Context HttpServletRequest request, @Context HttpServletResponse response)throws URISyntaxException, IOException, ServletException {
		HttpSession session=request.getSession();
		Contact user=(Contact)session.getAttribute("user");
		
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get("profilepic");
        if (blobKeys == null || blobKeys.isEmpty())
        {
        	request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else
        {
        	ofy().clear();                            
        Contact results = ofy().load().type(Contact.class).filter("email",user.getEmail()).first().now(); 
        ServingUrlOptions options = ServingUrlOptions.Builder
        		.withBlobKey(blobKeys.get(0))
        		.secureUrl(false);
        String servingUrl = ImagesServiceFactory.getImagesService().getServingUrl(options); 
        		
        results.setProfileurl(servingUrl);  
        ofy().save().entity(results).now();  
        session.setAttribute("user",results); 
        request.getRequestDispatcher("index.jsp").forward(request, response);
        }
	}
	    
	@SuppressWarnings("unused")
	@GET 
	@Path("/signinwithgoogle") 
	@Produces(MediaType.TEXT_HTML)
	public Response googlesignin(@QueryParam("code") String oauthcode, @QueryParam("error") String error,@Context HttpServletRequest request,@Context HttpServletResponse response) throws GeneralSecurityException, IOException,URISyntaxException, ServletException, org.json.simple.parser.ParseException
	{
		JSONObject result =new JSONObject();
		
		String client_id="280361308016-kmjfvue0neenakoq7oujg1mir75vevld.apps.googleusercontent.com"; 
        String client_secret="UxzcQ3l6KXlCPl6RZ4Ph0bQz";
        
		java.net.URI index = new java.net.URI("/index.jsp?signinerror=Invalid%20User");
		
		java.net.URI index1 = new java.net.URI("/index.jsp?signinerror=Invalid%20u"); 
		java.net.URI index3 = new java.net.URI("/index.jsp?signinerror=Your%20account%20is%20deactivated");
		java.net.URI location = new java.net.URI("/index.jsp");
		logger.info("OAuth Code :: " + oauthcode );

		if (error != null) {
			    return Response.temporaryRedirect(index1).build();

		}
		else {
			
			Client client = ClientBuilder.newClient();
			
			WebTarget target = client.target("https://www.googleapis.com/oauth2/v4/token");
			
			Form form = new Form();
			
			form.param("code", oauthcode).param("client_id", client_id).param("client_secret", client_secret)
					.param("redirect_uri", "https://timerapp-204808.appspot.com/signinwithgoogle")
					.param("grant_type", "authorization_code");
			
			Entity<Form> entity = Entity.form(form);
			
			Response tokenResponse = target.request(MediaType.APPLICATION_JSON).post(entity);
			
			if (tokenResponse.getStatus() == 200) {
				
				String tokens = tokenResponse.readEntity(String.class);
				
				tokenResponse.close();
				
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(tokens);
				
				target = client.target("https://www.googleapis.com/oauth2/v2/userinfo").queryParam("access_token",
						json.get("access_token"));
				
				Response userInfo = target.request(MediaType.APPLICATION_JSON).get();
				
				if (userInfo.getStatus() == 200) {
					
					String user = userInfo.readEntity(String.class);
					
					userInfo.close();
					
					json = (JSONObject) parser.parse(user);
					
					String email=(String) json.get("email");
					
					Contact results = ofy().load().type(Contact.class).filter("email",email).first().now();
					HttpSession session =request.getSession();
					
					
					
					if (results == null)
					{
						
						 session.invalidate();
				         return Response.temporaryRedirect(index).build();
						
					}
					else if(!results.getActive())
					{
						
						session.invalidate();
				         return Response.temporaryRedirect(index3).build(); 
					}
					else {
						
						session.setAttribute("user", results);
						 return Response.temporaryRedirect(location).build();	

					}

				}
				else
				{
					return Response.temporaryRedirect(index).build();	
				}

			} 
			else {
				
				 return Response.temporaryRedirect(index).build();
			}

		}
		 

	}
	@SuppressWarnings("unused")
	@GET 
	@Path("/googlesignup") 
	@Produces(MediaType.TEXT_HTML)
	public Response googlesignup(@QueryParam("code") String oauthcode, @QueryParam("error") String error,@Context HttpServletRequest request,@Context HttpServletResponse response) throws GeneralSecurityException, IOException,URISyntaxException, ServletException, org.json.simple.parser.ParseException
	{
      JSONObject result =new JSONObject();
		
		String client_id="280361308016-kmjfvue0neenakoq7oujg1mir75vevld.apps.googleusercontent.com"; 
        String client_secret="UxzcQ3l6KXlCPl6RZ4Ph0bQz";
        
		java.net.URI index = new java.net.URI("/index.jsp");
		
		java.net.URI index1 = new java.net.URI("/index.jsp?signinerror=Invalid%20u"); 
		java.net.URI index3 = new java.net.URI("/index.jsp?signinerror=Your%20account%20is%20deactivated");
		java.net.URI location = new java.net.URI("/index.jsp?signinerror=Email%20already%20registred");
		logger.info("OAuth Code :: " + oauthcode );

		if (error != null) {
			    return Response.temporaryRedirect(index1).build();

		}
		else {
			
			Client client = ClientBuilder.newClient();
			
			WebTarget target = client.target("https://www.googleapis.com/oauth2/v4/token");
			
			Form form = new Form();
			
			form.param("code", oauthcode).param("client_id", client_id).param("client_secret", client_secret)
					.param("redirect_uri", "https://timerapp-204808.appspot.com/googlesignup")
					.param("grant_type", "authorization_code");
			
			Entity<Form> entity = Entity.form(form);
			
			Response tokenResponse = target.request(MediaType.APPLICATION_JSON).post(entity);
			
			if (tokenResponse.getStatus() == 200) {
				
				String tokens = tokenResponse.readEntity(String.class);
				
				tokenResponse.close();
				
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(tokens);
				
				target = client.target("https://www.googleapis.com/oauth2/v2/userinfo").queryParam("access_token",
						json.get("access_token"));
				
				Response userInfo = target.request(MediaType.APPLICATION_JSON).get();
				
				if (userInfo.getStatus() == 200) {
					
					String user = userInfo.readEntity(String.class);
					
					userInfo.close();
					
					json = (JSONObject) parser.parse(user);
					
					String email=(String) json.get("email");
					String name=(String) json.get("given_name");
					String picture=(String)json.get("picture"); 
					
					Contact results = ofy().load().type(Contact.class).filter("email",email).first().now();
					HttpSession session =request.getSession();
					
					
					
					if (results == null)
					{
						Contact googleuser=new Contact();
						googleuser.setActive(true); 
						googleuser.setName(name); 
						googleuser.setProfileurl(picture); 
						googleuser.setEmail(email);  
						
						ofy().save().entity(googleuser).now();
						
						session.setAttribute("user", googleuser);
						
						 return Response.temporaryRedirect(index).build();	
						
						
						
					}
					else {
						
						 session.invalidate();
				         return Response.temporaryRedirect(location).build();

					}

				}
				else
				{
					return Response.temporaryRedirect(index).build();	
				}

			} 
			else {
				
				 return Response.temporaryRedirect(index).build();
			}

		}
		 

	}
	
}
