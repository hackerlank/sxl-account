package com.sohugame.sxl.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sohugame.sxl.servlet.login.AuthSkeyAction;
import com.sohugame.sxl.servlet.login.CheckSkeyAction;
import com.sohugame.sxl.servlet.login.LoginAction;
import com.sohugame.sxl.servlet.logout.LogoutAction;
import com.sohugame.sxl.servlet.reg.CheckAccountAction;
import com.sohugame.sxl.servlet.reg.RegAction;
import com.sohugame.sxl.util.LogUtil;

public class ActionDispatcher extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Locale locale;
	private String encoding;
	private String contextType;
	private Map<String, IHttpAction> actions = new HashMap<String, IHttpAction>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 初始化响应信息
		encoding = "UTF-8";
		locale = new Locale("zh", "CN");
		contextType = "text/html;charset=utf-8";
		actions.put("/reg", new RegAction());
		actions.put("/login", new LoginAction());
		actions.put("/checkAccountAction", new CheckAccountAction());
		actions.put("/closeClient", new LogoutAction());
		
		actions.put("/sp/checkSkey", new CheckSkeyAction());
		actions.put("/sp/authSkey", new AuthSkeyAction());
		actions.put("/sp/logoutGameServer", new LogoutAction());
		super.init(config);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest)arg0;
		HttpServletResponse resp = (HttpServletResponse)arg1;
		resp.setCharacterEncoding(encoding);
		resp.setContentType(contextType);
		resp.setLocale(locale);
		String key = req.getServletPath() + req.getPathInfo();
		LogUtil.d("key="+key);
		IHttpAction action = actions.get(key);
		if (null != action) {
			action.execute(req, resp);
		} else {
			StringBuilder sb = new StringBuilder("无效请求: ");
			sb.append("URI=[").append(key).append("],")
			.append("IP=").append(arg0.getRemoteAddr()).append("]");
			LogUtil.w(sb.toString());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
