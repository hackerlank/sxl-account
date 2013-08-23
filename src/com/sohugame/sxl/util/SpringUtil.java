package com.sohugame.sxl.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jolbox.bonecp.BoneCPDataSource;

public class SpringUtil {
	
	private static SpringUtil instance = new SpringUtil();
	private BoneCPDataSource writeDatasource;
	private BoneCPDataSource readDatasource;
	private ApplicationContext ctx;
	private SpringUtil(){
		ctx = new ClassPathXmlApplicationContext("resources/spring-bonecp.xml");
		writeDatasource = (BoneCPDataSource) ctx.getBean("writeSource");
		readDatasource = (BoneCPDataSource) ctx.getBean("readSource");
	}
	public static SpringUtil getInstance() {
		return instance;
	}
	public Connection getWriteConnection() {
		try {
			return writeDatasource.getConnection();
		} catch (SQLException e) {
			LogUtil.e("get writedb conn failed!", e);
		}
		return null;
	}
	public Connection getReadConnection() {
		try {
			return readDatasource.getConnection();
		} catch (SQLException e) {
			LogUtil.e("get readdb conn failed!", e);
		}
		return null;
	}
	public static void close(ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.e("close rs failed!", e);
			}
		}
	}
	public static void close(Statement stmt) {
		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
				LogUtil.e("close stmt failed!", e);
			}
		}
	}
	public static void close(Connection connection) {
		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				LogUtil.e("close connection failed!", e);
			}
		}
	}
	public void destory() {
		writeDatasource.close();
		readDatasource.close();
	}
	
	public static void main(String[] args) {
		try {
			SpringUtil.getInstance().getReadConnection().close();
			System.out.println("hah");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

