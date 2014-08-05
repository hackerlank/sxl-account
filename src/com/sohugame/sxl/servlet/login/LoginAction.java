package com.sohugame.sxl.servlet.login;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.login.GameServerDao;
import com.sohugame.sxl.module.login.GameServerPojo;
import com.sohugame.sxl.module.login.LoginDao;
import com.sohugame.sxl.module.reg.RegDao;
import com.sohugame.sxl.module.reg.RegPojo;
import com.sohugame.sxl.servlet.BasicHttpAction;
import com.sohugame.sxl.util.LogUtil;
import com.sohugame.sxl.util.MD5;

public class LoginAction extends BasicHttpAction {
	private static final String PARAM_DEVICE = "device";
	private Random skeyRand = new Random();
	@Override
	public JSONObject doAction(JSONObject args) throws JSONException {
		String device = args.getString(PARAM_DEVICE);
		long loginTime = System.currentTimeMillis();
		Integer playerId = 0;
		RegDao aDao = RegDao.getInstance();
		String ret = null;
		try {
			RegPojo regPojo = aDao.getOneByDevice(device);
			if (null == regPojo) {
				LogUtil.d("该账号不存在,准备为设备创建账号");
				regPojo = new RegPojo();
				regPojo.setAccountDevice(device);
				regPojo = RegDao.getInstance().insertAccount(regPojo);
				ret = makeSkey(regPojo);
				LoginDao.getInstance().saveOrUpdate(
						regPojo.getAccountPlayerId(), ret, loginTime);
				playerId = regPojo.getAccountPlayerId();
			} else if (regPojo.getAccountOnlineStatus().intValue() > 0) {
				LogUtil.d("该账号已经登录或者正在游戏中，请稍后登录");
				ret = "2";
			} else if (regPojo.getAccountDevice().equals(device)) {
				LogUtil.d("登录成功");
				ret = makeSkey(regPojo);
				LoginDao.getInstance().saveOrUpdate(
						regPojo.getAccountPlayerId(), ret, loginTime);
				playerId = regPojo.getAccountPlayerId();
			} else {
				LogUtil.d("密码错误登录失败");
				ret = "0";
			}
		} catch (SQLException e) {
			StringBuilder cause = new StringBuilder();
			cause.append("device=").append(device);
			LogUtil.e(cause.toString(), e);
		}
	
		String skey = ret;
		if (skey.length() != 32) {
			JSONObject json = new JSONObject();
			json.put("state", ret);
			return json;
		}
		List<GameServerPojo> gameServers = GameServerDao.getInstance().getAllGameServer();
		return wrapJson(skey, loginTime, gameServers,playerId);
	}
	
	public JSONObject wrapJson(String skey, long loginTime, List<GameServerPojo> gameServers,Integer playerId) throws JSONException {
		JSONObject retJsob = new JSONObject();
		retJsob.put("state", 1);
		retJsob.put("skey", skey);
		retJsob.put("seed", loginTime);
		JSONObject serverJsob = new JSONObject();
		for (GameServerPojo gsPojo : gameServers) {
			if (!gsPojo.getClosed()) {
				JSONObject value = new JSONObject();
				value.put("name", gsPojo.getServerName());
				value.put("ip", gsPojo.getExternalIp());
				value.put("httpPort", gsPojo.getExternalHttpPort());
				value.put("socketPort", gsPojo.getExternalSocketPort());
				value.put("state", 2);
				serverJsob.put(gsPojo.getServerId().toString(), value);
			}
		}
//		retJsob.put("recommend", Const.RECOMMEND_SERVERID);
		retJsob.put("serverList", serverJsob);
		return retJsob;
	}
	
	/**
	 * 创建SKEY.
	 * 
	 * @param accountId
	 * @return
	 */
	private String makeSkey(RegPojo regPojo) {
		StringBuilder plaintext = new StringBuilder("sohugame-sxl-");
		plaintext.append(regPojo.getAccountDevice());

		String ciphertext = MD5.digest(plaintext.toString());
		plaintext.delete(0, plaintext.length());

		plaintext.append(ciphertext).append('-').append(skeyRand.nextLong());

		return MD5.digest(plaintext.toString());
	}
}
