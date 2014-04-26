package com.footscout.rest.model;

import java.sql.*;
import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.footscout.rest.ConnectionHelper;

@XmlRootElement(name="player")
@XmlType(propOrder={
		"playerId",
		"attack",
		"defense",
		"skill",
		"physique",
		"teamwork"
})
public class PlayerKPI implements Comparable<PlayerKPI>{
	
	private int playerId;
	private double skill, defense, attack, physique, teamwork;
	private double dist;
	
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int player_id) {
		this.playerId = player_id;
	}

	public double getSkill() {
		return skill;
	}

	public void setSkill(double skill) {
		this.skill = skill;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}

	public double getPhysique() {
		return physique;
	}

	public void setPhysique(double physics) {
		this.physique = physics;
	}

	public double getTeamwork() {
		return teamwork;
	}

	public void setTeamwork(double team) {
		this.teamwork = team;
	}

	@XmlTransient
	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public PlayerKPI(){
		
	}
	
	public PlayerKPI(double[] kpi) {
		this.playerId = (int) kpi[0];
		this.skill = kpi[1];
		this.defense = kpi[2];
		this.attack = kpi[3];
		this.physique = kpi[4];
		this.teamwork = kpi[5];
		this.dist=0.0;
	}
	
	public String toString() {
		
        return this.playerId+","+this.skill+","+this.defense+","+this.attack+","+this.physique+","+this.teamwork;
    }

	@Override
	public int compareTo(PlayerKPI o) {
		
		return Double.compare(this.dist, o.dist);
	}
	
	public static List<PlayerKPI> getSimilarPlayers(int player_id){
	    
		Connection conn = null;
		Statement stmt = null;
		Vector<PlayerKPI> vPlayerKPI = new Vector<PlayerKPI>();
		List<PlayerKPI> similarPlayers = new ArrayList<PlayerKPI>();
		double _skill = 0.0, _defense=0.0, _attack=0.0, _physics=0.0, _team=0.0;
		// Pick first 5 players
		int K = 5;
		
		try{
			conn = ConnectionHelper.getConnection();
			stmt = conn.createStatement();
			//execute a query(player list)
			String sql;
			sql = "SELECT * FROM player_kpi WHERE player_id =" + player_id;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst() ) {    //player_id is not valid
		    	 System.out.println("No data");
		    	 return similarPlayers;
		    } 
			while(rs.next()){
				_skill = rs.getDouble("skill");
				_defense = rs.getDouble("defense");
				_attack = rs.getDouble("attack");
				_physics = rs.getDouble("physique");
				_team = rs.getDouble("teamwork");
			}
			
			sql = "SELECT * FROM player_kpi";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				double id = rs.getDouble("player_id");
				double skill = rs.getDouble("skill");
				double defense = rs.getDouble("defense");
				double attack = rs.getDouble("attack");
				double physics = rs.getDouble("physique");
				double team = rs.getDouble("teamwork");
				double[] kpi ={id,skill,defense,attack,physics,team};
				PlayerKPI player = new PlayerKPI(kpi);
				vPlayerKPI.add(player);				
			}	
			
		}
		catch(SQLException se){
			//handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//handle errors for Class.forName
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			if(conn!=null)
				ConnectionHelper.close(conn);
		}
		
		
		//Use KNN for finding similar players
		for(int i=0;i<vPlayerKPI.size();++i){
			vPlayerKPI.get(i).dist +=Math.pow(vPlayerKPI.get(i).skill-_skill, 2);
			vPlayerKPI.get(i).dist +=Math.pow(vPlayerKPI.get(i).defense-_defense, 2);
			vPlayerKPI.get(i).dist +=Math.pow(vPlayerKPI.get(i).attack-_attack, 2);
			vPlayerKPI.get(i).dist +=Math.pow(vPlayerKPI.get(i).physique-_physics, 2);
			vPlayerKPI.get(i).dist +=Math.pow(vPlayerKPI.get(i).teamwork-_team, 2);
		}
		// Sort the distance
		Collections.sort(vPlayerKPI);
		double val = vPlayerKPI.get(0).dist;
		for(int i = 0, rank = 0; i < vPlayerKPI.size() && rank < K; ++i) {
			if(val < vPlayerKPI.get(i).dist && player_id!=vPlayerKPI.get(i).playerId) {
				similarPlayers.add(vPlayerKPI.get(i));
				rank++;
			}
		}
	
		return similarPlayers;
	}
	
}