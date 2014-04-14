package com.footscout.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.footscout.rest.model.PlayerStat;

@Path("/stats")
public class StatResource {
	
	@GET @Path("/fp")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<PlayerStat> findAllFP(){
		return null;
	}
	
	@GET @Path("/gk")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void findAllGK(){
		return;
	}
}
