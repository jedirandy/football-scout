package com.footscout.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {
	private int id;
	private String name;
	private String team;
	private int teamId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getTeamId(){
		return teamId;
	}
	public void setTeamId(int teamId){
		this.teamId = teamId;
	}
}
