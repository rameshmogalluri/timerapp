package com.timer;
import static com.timer.ConfigureObjectify.ofy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
	public Response googlesignin(@QueryParam("code") String oauthcode, @QueryParam("error") String error,@Context HttpServletRequest request,@Context HttpServletResponse response) throws GeneralSecurityException, IOException,URISyntaxException, ServletException, ParseException, org.json.simple.parser.ParseException
	{
		JSONObject result =new JSONObject();
		
		String client_id="280361308016-ebcqotaemi6n5k5gj6rn1fuju5jv9fdg.apps.googleusercontent.com"; 
        String client_secret="GvnTgdIrdBL0i2LUTIb1M5OH";
        
		java.net.URI index = new java.net.URI("/index.jsp?signinerror=Invalid%20User");
		
		java.net.URI location = new java.net.URI("/index.jsp");

		if (error != null) {
			    return Response.temporaryRedirect(index).build();

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
					
					Contact login = ofy().load().type(Contact.class).filter("email", json.get("email")).first().now();
					
					if (login == null)
					{
						HttpSession session =request.getSession();
						session.invalidate();
				         return Response.temporaryRedirect(index).build();
					}
					else {
						HttpSession session = request.getSession();
						session.setAttribute("user", login);
						 return Response.temporaryRedirect(location).build();

					}

				}

			} 
			else {
				
				 return Response.temporaryRedirect(index).build();
			}

		}
		return Response.temporaryRedirect(index).build(); 

	}
	
}
