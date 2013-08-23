package com.sohugame.sxl.module.login;

/**
 * 游戏服务器数据对象
 * @author XZ.
 *
 */
public class GameServerPojo {
	private Integer serverId;
	private String serverName;
	private String externalIp;
	private Integer externalHttpPort;
	private Integer externalSocketPort;
	private String internalIp;
	private Integer internalHttpPort;
	private Integer internalSocketPort;
	private Boolean closed;
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getExternalIp() {
		return externalIp;
	}
	public void setExternalIp(String externalIp) {
		this.externalIp = externalIp;
	}
	public Integer getExternalHttpPort() {
		return externalHttpPort;
	}
	public void setExternalHttpPort(Integer externalHttpPort) {
		this.externalHttpPort = externalHttpPort;
	}
	public Integer getExternalSocketPort() {
		return externalSocketPort;
	}
	public void setExternalSocketPort(Integer externalSocketPort) {
		this.externalSocketPort = externalSocketPort;
	}
	public String getInternalIp() {
		return internalIp;
	}
	public void setInternalIp(String internalIp) {
		this.internalIp = internalIp;
	}
	public Integer getInternalHttpPort() {
		return internalHttpPort;
	}
	public void setInternalHttpPort(Integer internalHttpPort) {
		this.internalHttpPort = internalHttpPort;
	}
	public Integer getInternalSocketPort() {
		return internalSocketPort;
	}
	public void setInternalSocketPort(Integer internalSocketPort) {
		this.internalSocketPort = internalSocketPort;
	}
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
}
