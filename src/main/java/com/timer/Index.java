package com.timer;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/")
public class Index {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void index(@Context HttpServletRequest request, @Context HttpServletResponse response) throws URISyntaxException, IOException, ServletException 
	{
	request.getRequestDispatcher("index.jsp").forward(request, response);

	}
}
