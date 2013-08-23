package com.sohugame.sxl.module.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sohugame.sxl.util.LogUtil;
import com.sohugame.sxl.util.SpringUtil;

public class LoginDao {
	
	private static LoginDao instance = new LoginDao();

	private LoginDao() {
		super();
	}

	public static LoginDao getInstance() {
		return instance;
	}

	/**
	 * 根据skey取玩家ID.
	 * 
	 * @param skey
	 * @return
	 */
	public int getPlayerId(String skey) {
		String sql = "SELECT account_player_id FROM login_keys WHERE skey=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ret = 0;
		try {
			conn = SpringUtil.getInstance().getReadConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, skey);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			LogUtil.e("skey=" + skey, e);
			
			return -1;
		}finally{
			try {
				if (ps != null)ps.close();
				if (rs != null)rs.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
		return ret;
	}

	/**
	 * 根据playerId取skey.
	 * 
	 * @param playerId
	 * @return
	 */
	public String getSkey(Integer playerId) {
		String sql = "SELECT skey FROM login_keys WHERE account_player_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ret = null;
		try {
			conn = SpringUtil.getInstance().getReadConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, playerId);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			LogUtil.e("playerId=" + playerId, e);
			return null;
		}finally{
			try {
				if (ps != null)ps.close();
				if (rs != null)rs.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
		return ret;
	}

	/**
	 * 根据playerId更换skey.
	 * 
	 * @param playerId
	 * @param skey
	 */
	public void saveOrUpdate(Integer playerId, String skey, long timestamp) {
		String sql = "UPDATE login_keys SET skey=?,login_time=? WHERE account_player_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = SpringUtil.getInstance().getWriteConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, skey);
			ps.setTimestamp(2, new Timestamp(timestamp));
			ps.setInt(3, playerId);
			if (ps.executeUpdate() < 1) {
				ps.close();
				sql = "INSERT INTO login_keys VALUES(?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, playerId);
				ps.setString(2, skey);
				ps.setTimestamp(3, new Timestamp(timestamp));
				ps.executeUpdate();
			}
			ps.close();
		} catch (SQLException e) {
			LogUtil.e("playerId=" + playerId + ", skey=" + skey, e);
		}finally{
			try {
				if (ps != null)ps.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
	}
}
