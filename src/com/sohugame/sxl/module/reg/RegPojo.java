package com.sohugame.sxl.module.reg;

import java.util.Date;

public class RegPojo {
	private Integer accountPlayerId;
	private String accountDevice;
	private Date accountRegisterTime;
	private Date accountLoginTime;
	private Integer accountOnlineStatus;
	
	
	public String getAccountDevice() {
		return accountDevice;
	}
	public void setAccountDevice(String accountDevice) {
		this.accountDevice = accountDevice;
	}
	public Integer getAccountPlayerId() {
		return accountPlayerId;
	}
	public void setAccountPlayerId(Integer accountPlayerId) {
		this.accountPlayerId = accountPlayerId;
	}
	public Date getAccountRegisterTime() {
		return accountRegisterTime;
	}
	public void setAccountRegisterTime(Date accountRegisterTime) {
		this.accountRegisterTime = accountRegisterTime;
	}
	public Date getAccountLoginTime() {
		return accountLoginTime;
	}
	public void setAccountLoginTime(Date accountLoginTime) {
		this.accountLoginTime = accountLoginTime;
	}
	public Integer getAccountOnlineStatus() {
		return accountOnlineStatus;
	}
	public void setAccountOnlineStatus(Integer accountOnlineStatus) {
		this.accountOnlineStatus = accountOnlineStatus;
	}
	
	
}
