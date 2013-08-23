package com.sohugame.sxl.servlet.login;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.login.GameServerPojo;
import com.sohugame.sxl.module.login.LoginDao;
import com.sohugame.sxl.module.reg.RegDao;
import com.sohugame.sxl.servlet.ClusterAction;

/**
 * 根据SKEY授权请求服务器playerId可以登录.
 * @author XZ
 *
 */
public class AuthSkeyAction extends ClusterAction {

	@Override
	public Object doAction(JSONObject args, GameServerPojo gs) throws JSONException {
		String skey = args.getString("skey");
		int playerId = LoginDao.getInstance().getPlayerId(skey);
		if(playerId > 0) {
//			try {
//				RegDao.getInstance().updateOnlineStatus(playerId, gs.getServerId());
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		
		JSONObject jsob = new JSONObject();
		jsob.put("playerId", playerId);
		return jsob;
	}

}
