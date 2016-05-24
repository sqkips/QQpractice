package com.qq.common;

public class Message implements java.io.Serializable{
	private String mesType;
	private String sender;
	private String getter;
	private String content;
	private String sendTime;
	private boolean read_del;
	public boolean isRead_del() {
		return read_del;
	}

	public void setRead_del(boolean read_del) {
		this.read_del = read_del;
	}

	public String getMesType() {
		return mesType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	
	
}