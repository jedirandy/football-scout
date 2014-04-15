package com.footscout.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.footscout.rest.db.StatDAO;
import com.footscout.rest.model.Stat;

@Path("/stats")
public class StatResource {
	
	StatDAO statDAO = new StatDAO();
	@GET @Path("/fp")
	@Produces({MediaType.APPLICATION_XML})
	public List<Stat> findAll(){
		return statDAO.findAll();
	}
	
	@GET @Path("/fp/{id}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Stat> findById(@PathParam("id") String id){
		return statDAO.findByPlayerId(Integer.parseInt(id));
	}
	
/*	@GET @Path("/gk")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void findAllGK(){
		return;
	}*/
}
