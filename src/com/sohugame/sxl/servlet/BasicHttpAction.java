package com.sohugame.sxl.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohugame.sxl.crypt.AES;
import com.sohugame.sxl.crypt.Hex;
import com.sohugame.sxl.util.LogUtil;

public abstract class BasicHttpAction implements IHttpAction {

	private byte[] k = {-95,102,-8,-84,39,12,12,-24,-77,110,-13,-68,32,33,40,-2};
	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder read = new StringBuilder();
			for (String line; (line = in.readLine()) != null; read.append(line));
			in.close();
			LogUtil.d("get before string is = " + read.toString());
			byte[] reqData = AES.decrypt(Hex.parseHexStr2Byte(read.toString()), k);
//			byte[] reqData = read.toString().getBytes();
			LogUtil.d("get read string is = " + read.toString());
			if (null != reqData) {
				String param = new String(reqData, CHARSET);
//				String param = read.toString();
				JSONObject args = null;
				if (null!=param && !"".equals(param.trim())) {
					args = new JSONObject(param);
				} else {
					args = new JSONObject();
				}
				LogUtil.d(String.format("request[%s] param=%s", request.getPathInfo(), args));
				Object ret = doAction(args);
				LogUtil.d(String.format("response[%s] value=%s%n", request.getPathInfo(), ret));
				PrintWriter out = response.getWriter();
				if (null != ret) {
					param = Hex.parseByte2HexStr(AES.encrypt(ret.toString(), k));
//					param = ret.toString();
					out.write(param);
					out.flush();
				}
				out.close();
			} else {
				LogUtil.d(String.format("[%s]request[%s] data[%s] can not decrypt", 
						request.getRemoteAddr(), request.getPathInfo(), response.toString()));
				response.sendError(400, "I can't understand.");
			}
		} catch (IOException e) {
			LogUtil.e("", e);
		} catch (JSONException e) {
			LogUtil.e("", e);
		} catch (RuntimeException e) {
			LogUtil.e("", e);
		}
	}
	
	abstract public Object doAction(JSONObject args) throws JSONException;
}
