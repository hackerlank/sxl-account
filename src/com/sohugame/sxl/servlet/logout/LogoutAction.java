package com.sohugame.sxl.servlet.logout;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.logout.LogoutDao;
import com.sohugame.sxl.servlet.BasicHttpAction;
import com.sohugame.sxl.util.LogUtil;

public class LogoutAction extends BasicHttpAction {

	@Override
	public JSONObject doAction(JSONObject args) throws JSONException {
		Integer playerId = Integer.valueOf(args.getInt("playerId"));
		LogoutDao logoutDao = LogoutDao.getInstance();
		try {
			logoutDao.updateOnlineStatus(playerId, 0);
		} catch (SQLException e) {
			LogUtil.e(e);
		}
		return null;
	}

}
