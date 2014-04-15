package com.footscout.rest.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.footscout.rest.ConnectionHelper;
import com.footscout.rest.model.Player;

public class PlayerDAO {
	
	public List<Player> findAll(){
		List<Player> list = new ArrayList<Player>();
		Connection c = null;
		String query = "SELECT * FROM player_stat";
		try{
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()){
				list.add(processPlayerStat(rs));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			ConnectionHelper.close(c);
		}
		return list;
	}
	
	public Player findById(int id){
		String query = "SELECT * FROM player_stat WHERE player_id = ?";
		Player playerStat = null;
		Connection c = null;
		try{
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				playerStat = processPlayerStat(rs);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			ConnectionHelper.close(c);
		}
		return playerStat;
	}
	
	protected Player processPlayerStat(ResultSet rs) throws SQLException{
		Player playerStat = new Player();
		playerStat.setPlayerId(rs.getInt("player_id"));
		playerStat.setTeamId(rs.getInt("team_id"));
		playerStat.setPlayerName(rs.getString("player_name"));
		playerStat.setTeamName(rs.getString("team_name"));
		playerStat.setTimePlayed(rs.getInt("time_played"));
		playerStat.setPosition(rs.getInt("position"));
		playerStat.setJerseyNumber(rs.getInt("jersey_number"));
		playerStat.setGoal(rs.getInt("goal"));
		playerStat.setAssist(rs.getInt("assist"));
		playerStat.setYellowcard(rs.getInt("yellowcard"));
		playerStat.setRedcard(rs.getInt("redcard"));
		playerStat.setInjury(rs.getInt("injury"));
		playerStat.setBallTouched(rs.getDouble("ball_touched"));
		playerStat.setAppearance(rs.getInt("appearance"));
		playerStat.setPassPrecision(rs.getDouble("pass_precision"));
		playerStat.setShotPrecision(rs.getDouble("shot_precision"));
		playerStat.setZoneX(rs.getDouble("zone_x"));
		playerStat.setZoneY(rs.getDouble("zone_y"));
		playerStat.setAerialDuelWon(rs.getDouble("aerial_duel_won"));
		// KPIs
		playerStat.setAttack(rs.getDouble("attack"));
		playerStat.setDefense(rs.getDouble("defense"));
		playerStat.setSkill(rs.getDouble("skill"));
		playerStat.setPhysique(rs.getDouble("physique"));
		playerStat.setTeamwork(rs.getDouble("teamwork"));
		return playerStat;
	}
}
