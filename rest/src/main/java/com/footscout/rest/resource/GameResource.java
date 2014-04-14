package com.footscout.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.footscout.rest.db.GameDAO;
import com.footscout.rest.model.Game;

@Path("/games")
public class GameResource {
	
	GameDAO dao = new GameDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Game> findAll(){
		return dao.findAll();
	}
}
