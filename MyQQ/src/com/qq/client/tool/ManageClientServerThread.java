/*
 * ����ͻ��������������ͨѶ���߳���
 * 
 *���� ���շ����ת����������Ϣ��Thread
 * */
package com.qq.client.tool;

import java.util.HashMap;

public class ManageClientServerThread {
	 private static HashMap hashMap = new HashMap<String, ClientConServerThread>();
//	�Ѵ����õ�clientConServerThread���뵽hashmap
	public static void addClientConServerThread(String qqId,ClientConServerThread clientConServerThread){
		hashMap.put(qqId, clientConServerThread);
	}
//	����ͨ��qqIdȡ�ø��߳�
	public static ClientConServerThread getClientConServerThread(String  qqId){
		return (ClientConServerThread) hashMap.get(qqId);
	}

}
