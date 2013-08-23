package com.sohugame.sxl.servlet.reg;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.reg.RegDao;
import com.sohugame.sxl.module.reg.RegPojo;
import com.sohugame.sxl.servlet.BasicHttpAction;
import com.sohugame.sxl.util.LogUtil;

public class RegAction extends BasicHttpAction {
	static final String PARAM_USERNAME = "username";
	static final String PARAM_PSWD = "password";
//	static final String PARAM_QUESTION = "question";
//	static final String PARAM_ANSWER = "answer";
//	static final String PARAM_CID = "cid";

	@Override
	public JSONObject doAction(JSONObject args) throws JSONException {
		String username = args.getString(PARAM_USERNAME);
		String password = args.getString(PARAM_PSWD);
//		Integer question = Integer.valueOf(args.getInt(PARAM_QUESTION));
//		String answer = args.getString(PARAM_ANSWER);
//		Integer regChannelId = Integer.valueOf(args.optInt(PARAM_CID, 0));
		RegPojo aPojo = new RegPojo();
//		aPojo.setAccountUsername(username);
//		aPojo.setAccountPassword(password);
//		aPojo.setAccountSecurityQuestion(question);
//		aPojo.setAccountSecrityAnswer(answer);
//		aPojo.setAccountRegChannelId(regChannelId);
		RegDao aDao = RegDao.getInstance();
		int ret = 0;
		try {
			aDao.insertAccount(aPojo);
			ret = 1;
			LogUtil.d("数据插入account成功!");
		} catch (SQLException e) {
			StringBuilder msg = new StringBuilder("数据插入account失败!");
			msg.append("username=").append(username).append(", password=")
					.append(password);
			LogUtil.e(msg.toString(), e);
			ret = 0;
		}
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
