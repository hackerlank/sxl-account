package com.sohugame.sxl.module.reg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sohugame.sxl.util.LogUtil;
import com.sohugame.sxl.util.SpringUtil;

public class RegDao {
	private static RegDao instance = new RegDao();

	private RegDao() {
		super();
	}

	public static RegDao getInstance() {
		return instance;
	}

	/**
	 * 插入新数据.
	 * 
	 * @param aPojo
	 * @throws SQLException
	 */
	public RegPojo insertAccount(RegPojo aPojo) throws SQLException {
		String sql = "INSERT INTO accounts VALUES(NULL,?,now(),0)";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = SpringUtil.getInstance().getWriteConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, aPojo.getAccountDevice());
//			ps.setInt(3, aPojo.getAccountSecurityQuestion());
//			ps.setString(4, aPojo.getAccountSecrityAnswer());
//			ps.setInt(5, aPojo.getAccountRegChannelId());
			System.out.println("插入用户账户"+aPojo.getAccountDevice());
			ps.executeUpdate();
			ps.close();
		}catch (Exception e) {
		}finally{
			try {
				if (ps != null)ps.close();
				if (rs != null)rs.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
		return getOneByDevice(aPojo.getAccountDevice());
	}

	/**
	 * 检查重复username.
	 * 
	 * @param username
	 * @return
	 */
	public int checkAccount(String device) {

		String sql = "SELECT COUNT(1) FROM accounts WHERE account_device=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ret = 0;
		try {
			conn = SpringUtil.getInstance().getReadConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, device);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			LogUtil.e("device=" + device, e);
			return 1;
		}finally{
			try {
				if (ps != null)ps.close();
				if (rs != null)rs.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
		if (ret > 0) {
			LogUtil.d("after DB=" + ret + ", duplicate device=" + device);
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * 根据用户名取一个账户数据对象.
	 * @param device - 用户名
	 * @return
	 * @throws SQLException
	 */
	public RegPojo getOneByDevice(String device) throws SQLException {
		String sql = "SELECT * FROM accounts WHERE account_device=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		RegPojo aPojo = null;
		try {
			conn = SpringUtil.getInstance().getReadConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, device);
			rs = ps.executeQuery();
			if (rs.next()) {
				aPojo = new RegPojo();
				aPojo.setAccountPlayerId(rs.getInt("account_player_id"));
				aPojo.setAccountDevice(rs.getString("account_device"));
				aPojo.setAccountRegisterTime(rs.getDate("account_register_time"));
				aPojo.setAccountOnlineStatus(rs.getInt("account_online_status"));
			}
			rs.close();
			ps.close();
		}catch (Exception e) {
		}finally{
			try {
				if (ps != null)ps.close();
				if (rs != null)rs.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
		return aPojo;
	}
	
	/**
	 * 更新在线状态.
	 * @param playerId
	 * @param servId
	 * @throws SQLException
	 */ 
	public void updateOnlineStatus(Integer playerId, Integer servId) throws SQLException {
		String sql = "UPDATE accounts SET account_online_status=? WHERE account_player_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = SpringUtil.getInstance().getWriteConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, servId);
			ps.setInt(2, playerId);
			ps.executeUpdate();
			ps.close();
			LogUtil.d("playerId=[" + playerId +"], online status=["+servId+"] ok!");
		}catch (Exception e) {
		}finally{
			try {
				if (ps != null)ps.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
	}
}
