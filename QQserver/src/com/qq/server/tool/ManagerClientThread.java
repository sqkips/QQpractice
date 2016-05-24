package com.qq.server.tool;

import java.util.HashMap;
import java.util.Iterator;

import com.qq.server.model.SerConClientThread;

public class ManagerClientThread {
	public static HashMap hashMap =new HashMap<String,SerConClientThread>(){};//必须是个静态的 不然会有多份hashmap
//	向hashMap中添加一个线程
	public static  void addClientThread(String uid ,SerConClientThread serConClientThread){
		hashMap.put(uid, serConClientThread);
	}

	public static SerConClientThread getClientThread(String uid){
		return (SerConClientThread)hashMap.get(uid);
	}
	public static  void delClientThread(String uid){//把不需要的线程从从hashmap中删除，
		SerConClientThread serConClientThreadcopy= (SerConClientThread) hashMap.get(uid);
		hashMap.remove(uid);

	}
	
	//返回当前在线的人的情况
	public static String getALLOnLineUserid(){
		//使用迭代器完成
		Iterator it= hashMap.keySet().iterator();
		String res= "";
		while(it.hasNext()){
			res+=it.next().toString()+" ";
		}
		return res;
	}
	
	
}
