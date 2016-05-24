/*
 * 用户信息类
 * */
package com.qq.common;

public class User implements java.io.Serializable{
	/**
	 * 
	 */
	private String userid;
	private String passwd;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
