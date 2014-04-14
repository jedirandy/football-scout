package com.footscout.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.footscout.rest.db.PlayerDAO;
import com.footscout.rest.model.PlayerStat;

@Path("/players")
public class PlayerResource {
	
	PlayerDAO dao = new PlayerDAO();
	
	@GET
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_XML)
	public List<PlayerStat> findAll(){
		return dao.findAllPlayerStat();
	}
	
	@GET @Path("{id}")
	@Produces({MediaType.APPLICATION_XML})
	public PlayerStat findStatById(@PathParam("id") String id){
		return dao.findStatById(Integer.parseInt(id));
	} 
	
	@GET @Path("/abc")
	@Produces("text/plain")
	public String getMessage(){
		return "Hello";
	}
}
