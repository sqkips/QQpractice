/*
 * �����û�����������
 * */package com.qq.client.tool;

import java.util.HashMap;

import com.qq.client.view.QQChat;

public class ManageQQChat {

	private static HashMap hashMap = new HashMap<String, QQChat>();
	//����
	public static void addqqChat(String LoginIdAndFriendId,QQChat qqChat)
	{
		hashMap.put(LoginIdAndFriendId,qqChat);
	}
	//ȡ��
	public static QQChat getqqChat(String LoginIdAndFriendId)
	{
		return (QQChat) hashMap.get(LoginIdAndFriendId);
				
	}
}
