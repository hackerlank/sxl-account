package com.sohugame.sxl.servlet.reg;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.reg.RegDao;
import com.sohugame.sxl.servlet.BasicHttpAction;
import com.sohugame.sxl.util.LogUtil;

public class CheckAccountAction extends BasicHttpAction {
	private static String PARAM_ACCOUNT = "account";
	@Override
	public JSONObject doAction(JSONObject args) throws JSONException {
		String username = args.getString(PARAM_ACCOUNT);
		RegDao regDao = RegDao.getInstance();
		int ret = regDao.checkAccount(username);
		return wrapJson(ret);
	}
	
	public JSONObject wrapJson(int result){
		JSONObject json = new JSONObject();
		try {
			json.put("state", result);
		} catch (JSONException e) {
			LogUtil.e("check account error!",e);
		}
		return json;
	}

}
