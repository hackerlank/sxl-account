package com.sohugame.sxl.util;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;

/**
 * 日志工具类.
 * @author XZ
 *
 */
public class LogUtil {
	
	static {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		String cfgName = cl.getResource("resources/log4j.properties").getPath();
		PropertyConfigurator.configureAndWatch(cfgName, 300000L);
		//PropertyConfigurator.configureAndWatch("WEB-INF/classes/resource/log4j.properties", 300000L);
	}
	
	/**
	 * ERROR.
	 * @param t - 异常类
	 */
	public static void e(Throwable t) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.error(null, t.fillInStackTrace());
		NDC.pop();
		NDC.remove();
	}
	
	/**
	 * ERROR.
	 * @param message - 文字内容
	 */
	public static void e(String message) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.error(message);
		NDC.pop();
		NDC.remove();
	}
	
	/**
	 * ERROR.
	 * @param message - 文字内容
	 * @param t - 异常类
	 */
	public static void e(String message, Throwable t) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.error(message, t);
		NDC.pop();
		NDC.remove();
	}
	
	/**
	 * WARN.
	 * @param message - 文字内容
	 */
	public static void w(String message) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.warn(message);
		NDC.pop();
		NDC.remove();
	}
	
	/**
	 * WARN.
	 * @param message - 文字内容
	 * @param t - 异常类
	 */
	public static void w(String message, Throwable t) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.warn(message, t);
		NDC.pop();
		NDC.remove();
	}
	
	/**
	 * INFO.
	 * @param message - 文字信息
	 */
	public static void i(String message) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.info(message);
		NDC.pop();
		NDC.remove();
	}
	
	/**
	 * DEBUG.
	 * @param message - 文字内容
	 */
	public static void d(String message) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		NDC.push(ste[2].getMethodName() + ":" + ste[2].getLineNumber());
		Logger logger = Logger.getLogger(ste[2].getClassName());
		logger.debug(message);
		NDC.pop();
		NDC.remove();
	}
	
	public static void main(String[] args) {
	}
}
