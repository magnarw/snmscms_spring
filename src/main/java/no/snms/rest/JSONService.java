package no.snms.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import no.snms.dao.News;
import no.snms.repository.JummaRepository;
import no.snms.repository.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@Path("/json")
public class JSONService {
	
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	JummaRepository jummaRepository;
	
	@GET
	@Path("/news")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNews(@QueryParam(value = "pageSize") String pageSize,@QueryParam(value = "pageNumber") String pageNumber,@QueryParam(value = "filter") String filter) {
		try {
			return Response.status(201).entity(newsRepository.listNews(pageSize,pageNumber,filter)).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	@GET
	@Path("/jumma")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJumma() {
		try {
			return Response.status(201).entity(jummaRepository.listJumma()).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	
	/*

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();
		
	}
	*/
	
}