/*
 * 管理好友 黑名单 界面类
 * */

package com.qq.client.tool;

import java.util.HashMap;

import com.qq.client.view.QQFriendList;

public class ManageQQFriendList {
		private static HashMap hashMap = new HashMap<String, QQFriendList>();
		public static void addQQFriendList(String qqId,QQFriendList qqFriend){
			hashMap.put(qqId,qqFriend);
		}		
		public static QQFriendList getFriendLIst(String qqId){
			return (QQFriendList) hashMap.get(qqId);
		}
}
