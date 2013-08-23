package com.sohugame.sxl.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.module.login.GameServerDao;
import com.sohugame.sxl.module.login.GameServerPojo;
import com.sohugame.sxl.util.LogUtil;

/**
 * 服务器间动作.
 * 
 * @author XZ
 * 
 */
public abstract class ClusterAction implements IHttpAction {
	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String param = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder read = new StringBuilder();
			for (String line; (line = in.readLine()) != null; read.append(line));
			in.close();
			param = URLDecoder.decode(read.toString(), CHARSET);
			JSONObject args = null;
			if (null != param && !"".equals(param.trim())) {
				args = new JSONObject(param);
			} else {
				args = new JSONObject();
			}
			String remoteAddr = request.getRemoteAddr();
			LogUtil.d(String.format("[%s] request[%s] param=%s", remoteAddr, request.getPathInfo(), args));
			PrintWriter out = response.getWriter();
			GameServerPojo gs = GameServerDao.getInstance().get(remoteAddr);
			Object ret = null;
			if (gs != null) {
				ret = doAction(args, gs);
				if (null != ret) {
					out.write(ret.toString());
					out.flush();
				}
				LogUtil.d(String.format("[%s] response[%s] value=%s%n", remoteAddr, request.getPathInfo(), ret));
			} else {
				response.sendError(400, "I don't know you.");
				LogUtil.d(String.format("[%s] response[%s] sendError=400%n", remoteAddr, request.getPathInfo()));
			}
			out.close();
		} catch (IOException e) {
			LogUtil.e("", e);
		} catch (JSONException e) {
			LogUtil.e("param="+param, e);
		} catch (RuntimeException e) {
			LogUtil.e("", e);
		}
	}
	
	/**
	 * 请求IP是否为内部已认可的IP.
	 * @param remoteAddr
	 * @return
	 */
	protected boolean isAuthIp(String remoteAddr) {
		List<GameServerPojo> servers = GameServerDao.getInstance().getAllGameServer();
		if (servers == null) {
			return false;
		}
		for (GameServerPojo sPojo : servers) {
			if(remoteAddr.equals(sPojo.getInternalIp())) {
				return true;
			}
		}
		return false;
	}
	
	abstract public Object doAction(JSONObject args, GameServerPojo gs) throws JSONException;
}
