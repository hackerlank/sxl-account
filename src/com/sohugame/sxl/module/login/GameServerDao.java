package com.sohugame.sxl.module.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.sohugame.sxl.util.LogUtil;
import com.sohugame.sxl.util.SpringUtil;

/**
 * 游戏服务器数据.
 * @author XZ
 *
 */
public class GameServerDao {
	
	private static GameServerDao instance = new GameServerDao();
	
	private GameServerDao() {
		super();
	}
	
	public static GameServerDao getInstance() {
		return instance;
	}
	
	public GameServerPojo get(String ip) {
		Connection conn = null;
		String sql = "SELECT * FROM game_servers WHERE closed=0 AND internal_ip=?";
		try {
			conn = SpringUtil.getInstance().getReadConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ip);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				GameServerPojo gsPojo = new GameServerPojo();
				gsPojo.setServerId(rs.getInt("server_id"));
				gsPojo.setServerName(rs.getString("server_name"));
				gsPojo.setExternalIp(rs.getString("external_ip"));
				gsPojo.setExternalHttpPort(rs.getInt("external_http_port"));
				gsPojo.setExternalSocketPort(rs.getInt("external_socket_port"));
				gsPojo.setInternalIp(rs.getString("internal_ip"));
				gsPojo.setInternalHttpPort(rs.getInt("internal_http_port"));
				gsPojo.setInternalSocketPort(rs.getInt("internal_socket_port"));
				gsPojo.setClosed(rs.getBoolean("closed"));
				return gsPojo;
			}
		} catch (SQLException e) {
			LogUtil.e("", e);
		} finally {
			SpringUtil.close(conn);
		}
		return null;
	}
	
	/**
	 * 取所有游戏服务器.
	 * @return
	 */
	public List<GameServerPojo> getAllGameServer() {
		List<GameServerPojo> gameServers = new LinkedList<GameServerPojo>();
		Connection conn = null;
		try {
			conn = SpringUtil.getInstance().getReadConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM game_servers");
			while(rs.next()) {
				GameServerPojo gsPojo = new GameServerPojo();
				gsPojo.setServerId(rs.getInt("server_id"));
				gsPojo.setServerName(rs.getString("server_name"));
				gsPojo.setExternalIp(rs.getString("external_ip"));
				gsPojo.setExternalHttpPort(rs.getInt("external_http_port"));
				gsPojo.setExternalSocketPort(rs.getInt("external_socket_port"));
				gsPojo.setInternalIp(rs.getString("internal_ip"));
				gsPojo.setInternalHttpPort(rs.getInt("internal_http_port"));
				gsPojo.setInternalSocketPort(rs.getInt("internal_socket_port"));
				gsPojo.setClosed(rs.getBoolean("closed"));
				gameServers.add(gsPojo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LogUtil.e("", e);
		} finally {
			SpringUtil.close(conn);
		}
		return gameServers;
	}
}
