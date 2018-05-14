package com.timer;
import static com.timer.ConfigureObjectify.ofy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import javax.ws.rs.Produces;
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
	
}
