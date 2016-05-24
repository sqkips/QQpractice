/*
 * 管理用户聊天界面的类
 * */package com.qq.client.tool;

import java.util.HashMap;

import com.qq.client.view.QQChat;

public class ManageQQChat {

	private static HashMap hashMap = new HashMap<String, QQChat>();
	//加入
	public static void addqqChat(String LoginIdAndFriendId,QQChat qqChat)
	{
		hashMap.put(LoginIdAndFriendId,qqChat);
	}
	//取出
	public static QQChat getqqChat(String LoginIdAndFriendId)
	{
		return (QQChat) hashMap.get(LoginIdAndFriendId);
				
	}
}
