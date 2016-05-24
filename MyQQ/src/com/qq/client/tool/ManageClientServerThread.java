/*
 * 管理客户端与服务器保持通讯的线程类
 * 
 *管理 接收服务端转发过来的消息的Thread
 * */
package com.qq.client.tool;

import java.util.HashMap;

public class ManageClientServerThread {
	 private static HashMap hashMap = new HashMap<String, ClientConServerThread>();
//	把创建好的clientConServerThread放入到hashmap
	public static void addClientConServerThread(String qqId,ClientConServerThread clientConServerThread){
		hashMap.put(qqId, clientConServerThread);
	}
//	可以通过qqId取得该线程
	public static ClientConServerThread getClientConServerThread(String  qqId){
		return (ClientConServerThread) hashMap.get(qqId);
	}

}
