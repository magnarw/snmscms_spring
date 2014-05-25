package no.snms.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import no.snms.dao.News;
import no.snms.repository.JummaRepository;
import no.snms.repository.NewsRepository;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.log4j.Logger;

@Component
@Path("/json")
public class PublicJsonService {
	
	private static final Logger logger = Logger.getLogger(PublicJsonService.class);
	
	@Value("${snms.uploaddir}")
	private String serverUploadLoctionFolder; 
	
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	JummaRepository jummaRepository;
	
	@GET
	@Path("/news")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNews(@QueryParam(value = "pageSize") String pageSize,@QueryParam(value = "pageNumber") String pageNumber,@QueryParam(value = "filter") String filter,@QueryParam(value = "includePri") boolean includePri ) {
		try {
			return Response.status(201).entity(newsRepository.listNews(pageSize,pageNumber,filter, includePri)).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	

	
	@GET
	@Path("/image/{imageFileName}")
	@Produces("image/png")
	public Response getImage(@PathParam(value="imageFileName") String imageFileName, @QueryParam(value="h") Integer h, @QueryParam(value="w") Integer w ) {
		String filename = serverUploadLoctionFolder + imageFileName;
		logger.debug("Trying to find image " + filename);
		File imageFile = new File(filename);
		String ext = FilenameUtils.getExtension(imageFileName);
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			if(h!=null && w!=null)
				image = Scalr.resize(image, Mode.AUTOMATIC, w, h);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, ext, baos);
			byte[] imageData = baos.toByteArray();
			return Response.ok(imageData).build();
		} catch (IOException e) {
			logger.error("Could not find image. Message="  + e.getMessage());
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

}