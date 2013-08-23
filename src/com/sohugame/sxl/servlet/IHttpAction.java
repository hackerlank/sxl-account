package com.sohugame.sxl.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHttpAction {
	public static final String CHARSET = "UTF-8";
	public void execute(HttpServletRequest req,HttpServletResponse resp);
}
