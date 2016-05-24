/*
 * 客户端连接服务器部分
 * */
package com.qq.client.model;

import java.util.*;
import java.net.*;
import java.io.*;

import jdk.internal.util.xml.impl.Input;

import com.qq.client.tool.ClientConServerThread;
import com.qq.client.tool.ManageClientServerThread;
import com.qq.client.tool.ManageClientServerThread;
import com.qq.common.Message;
import com.qq.common.User;
public class MyQQClientConnectSer  {
		private  Socket s;
	public Socket getS() {
			return s;
		}

	//发送第一次请求
	public boolean SendLoginInfoToServer(Object o) throws ClassNotFoundException{
		boolean b= false;
		try {

			 s = new Socket("10.95.71.90",9999);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			User u =(User)o;
			System.out.print(u.getUserid());
			out.writeObject(o);
			
			ObjectInputStream in =new ObjectInputStream(s.getInputStream());
			Message message = (Message) in.readObject();
			//这里就是验证用户登录的地方
			if(message.getMesType().equals("1"))		//mestype1表示成功 2表示失败3表示普通包消息
			{
				//创建一个该qq号和服务器端保持通讯连接的线程
				ClientConServerThread clientConServerThread = new ClientConServerThread(s);
				//启动线程
				clientConServerThread.start();
				ManageClientServerThread.addClientConServerThread(u.getUserid(), clientConServerThread);
				b =   true;
			}
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
			return b;
	
	}
	
	public void SendInfoToServer(Object o) {
		
	}
	
	
	
}
