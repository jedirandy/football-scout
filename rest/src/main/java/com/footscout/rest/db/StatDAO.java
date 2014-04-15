package com.footscout.rest.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.footscout.rest.BeanHelper;
import com.footscout.rest.ConnectionHelper;
import com.footscout.rest.model.Stat;

public class StatDAO {
	private BeanHelper beanHelper = new BeanHelper(Stat.class);
	
	public List<Stat> findAll() {
		List<Stat> list = new ArrayList<Stat>();
		Connection c = null;
		String query = "SELECT * FROM performance_fp";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Stat stat = new Stat();
				beanHelper.callSetter(stat, rs);
				list.add(stat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public List<Stat> findByPlayerId(int id) {
		List<Stat> list = new ArrayList<Stat>();
		String query = "SELECT * FROM performance_fp WHERE player_id = ?";
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Stat stat = new Stat();
				beanHelper.callSetter(stat, rs);
				list.add(stat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	protected Stat processStat(ResultSet rs) throws SQLException {
		Stat stat = new Stat();
		stat.setAerialDuelWon(rs.getDouble("aerial_duel_won"));
		Class<Stat> c = Stat.class;
		return stat;
	}
}
