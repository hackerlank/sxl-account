package com.sohugame.sxl.module.logout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sohugame.sxl.util.LogUtil;
import com.sohugame.sxl.util.SpringUtil;

public class LogoutDao {
	
	private static LogoutDao instance = new LogoutDao();

	private LogoutDao() {
		super();
	}

	public static LogoutDao getInstance() {
		return instance;
	}

	/**
	 * 更新在线状态.
	 * 
	 * @param playerId
	 * @param servId
	 * @throws SQLException
	 */
	public void updateOnlineStatus(Integer playerId, Integer servId)
			throws SQLException {
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
			LogUtil.d("playerId=[" + playerId + "], online status=[" + servId
					+ "] ok!");
		}finally{
			try {
				if (ps != null)ps.close();
				}catch (SQLException e) {}
			SpringUtil.close(conn);
		}
	}
}
