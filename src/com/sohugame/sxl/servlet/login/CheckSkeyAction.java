package com.sohugame.sxl.servlet.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.login.GameServerPojo;
import com.sohugame.sxl.module.login.LoginDao;
import com.sohugame.sxl.servlet.ClusterAction;

/**
 * 验证SKEY.
 * @author XZ
 *
 */
public class CheckSkeyAction extends ClusterAction {

	@Override
	public Object doAction(JSONObject args, GameServerPojo gs) throws JSONException {
		String skey = args.getString("skey");
		int playerId = LoginDao.getInstance().getPlayerId(skey);
		JSONObject jsob = new JSONObject();
		jsob.put("playerId", playerId);
		return jsob;
	}

}
