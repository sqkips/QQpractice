package com.qq.server.tool;

import java.util.HashMap;
import java.util.Iterator;

import com.qq.server.model.SerConClientThread;

public class ManagerClientThread {
	public static HashMap hashMap =new HashMap<String,SerConClientThread>(){};//�����Ǹ���̬�� ��Ȼ���ж��hashmap
//	��hashMap�����һ���߳�
	public static  void addClientThread(String uid ,SerConClientThread serConClientThread){
		hashMap.put(uid, serConClientThread);
	}

	public static SerConClientThread getClientThread(String uid){
		return (SerConClientThread)hashMap.get(uid);
	}
	public static  void delClientThread(String uid){//�Ѳ���Ҫ���̴߳Ӵ�hashmap��ɾ����
		SerConClientThread serConClientThreadcopy= (SerConClientThread) hashMap.get(uid);
		hashMap.remove(uid);

	}
	
	//���ص�ǰ���ߵ��˵����
	public static String getALLOnLineUserid(){
		//ʹ�õ��������
		Iterator it= hashMap.keySet().iterator();
		String res= "";
		while(it.hasNext()){
			res+=it.next().toString()+" ";
		}
		return res;
	}
	
	
}
