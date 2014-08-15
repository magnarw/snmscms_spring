package no.snms.rest;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import no.snms.dao.Image;
import no.snms.dao.Jumma;
import no.snms.dao.News;
import no.snms.repository.JummaRepository;
import no.snms.repository.NewsRepository;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
@Path("/admin")
public class AdminJsonService {
	
	
	private static final Logger logger = Logger.getLogger(AdminJsonService.class);
	
	@Value("${snms.uploaddir}")
	private String serverUploadLoctionFolder; 
	
	@Value("${snms.imagedownloadurl}")
	private String immageDownloadUrl; 
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	JummaRepository jummaRepository;
	
	
	@POST
	@Path("/news")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateNews(News newsToUpdate){
		try {
			logger.debug("Trying to update news" + newsToUpdate); 
			newsRepository.updateNews(newsToUpdate);
			return Response.status(200).entity("ok").build();
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Could not update news.Message=" + e.getMessage()); 
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	
	@POST
	@Path("/jumma")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateJumma(List<Jumma> jummas){
		try {
			logger.debug("Trying to update jumma"); 
			jummaRepository.updateJumma(jummas);
			return Response.status(200).entity("ok").build();
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Could not update news.Message=" + e.getMessage()); 
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	@DELETE
	@Path("/jumma")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delteJumma(Jumma jumma){
		try {
			logger.debug("Trying remove jumma"); 
			jummaRepository.deleteJumma(jumma);
			return Response.status(200).entity("ok").build();
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Could not update news.Message=" + e.getMessage()); 
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	
	
	@POST
	@Path("/news/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNews(News newToDelete){
		try {
			logger.debug("Trying to delete news" + newToDelete); 
			newsRepository.removeNews(newToDelete);
			return Response.status(200).entity("ok").build();
		}
		catch(Exception e) {
			logger.error("Could not delete news.Message=" + e.getMessage()); 
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
		String filePath = serverUploadLoctionFolder
				+ contentDispositionHeader.getFileName();
		// save the file to the server
		saveFile(fileInputStream, filePath);
		
		createXtraSmallVersion(filePath);
		createSmallVersion(filePath);
		createMediumVersion(filePath);
		createLagreVersion(filePath);

		Image image = new Image();
		image.setImageUrl( immageDownloadUrl + contentDispositionHeader.getFileName());
		return Response.status(200).entity(image).build();
	}

	private void createSmallVersion(String filePath) {
		File imageFile = new File(filePath);
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			image = Scalr.resize(image, Mode.AUTOMATIC, 400, 400);
			String ext = FilenameUtils.getExtension(filePath);
			String [] temp = filePath.split("\\.");
			File outputFile = new File(temp[0] + "_small." + temp[1]);
			ImageIO.write(image, ext,outputFile);
		} catch (IOException e) {
			logger.error("Could not find file:" + filePath);
			e.printStackTrace();
		}
		
	}
	
	private void createXtraSmallVersion(String filePath) {
		File imageFile = new File(filePath);
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			image = Scalr.resize(image, Mode.AUTOMATIC, 200, 200);
			String ext = FilenameUtils.getExtension(filePath);
			String [] temp = filePath.split("\\.");
			File outputFile = new File(temp[0] + "_extrasmall." + temp[1]);
			ImageIO.write(image, ext,outputFile);
		} catch (IOException e) {
			logger.error("Could not find file:" + filePath);
			e.printStackTrace();
		}
		
	}
	
	
	
	private void createMediumVersion(String filePath) {
		File imageFile = new File(filePath);
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			image = Scalr.resize(image, Mode.AUTOMATIC, 600, 600);
			String ext = FilenameUtils.getExtension(filePath);
			String [] temp = filePath.split("\\.");
			File outputFile = new File(temp[0] + "_medium." + temp[1]);
			ImageIO.write(image, ext,outputFile);
		} catch (IOException e) {
			logger.error("Could not find file:" + filePath);
			e.printStackTrace();
		}
		
	}
	
	
	private void createLagreVersion(String filePath) {
		File imageFile = new File(filePath);
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			image = Scalr.resize(image, Mode.AUTOMATIC, 1000, 1000);
			String ext = FilenameUtils.getExtension(filePath);
			String [] temp = filePath.split("\\.");
			File outputFile = new File(temp[0] + "_lagre." + temp[1]);
			ImageIO.write(image, ext,outputFile);
		} catch (IOException e) {
			logger.error("Could not find file:" + filePath);
			e.printStackTrace();
		}
		
	}


	private void saveFile(InputStream uploadedInputStream, String serverLocation) {
		try {
			OutputStream outpuStream = new FileOutputStream(new File(
					serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
			logger.error("Could not upload image, Message=" + e.getMessage());
		}
	}

}