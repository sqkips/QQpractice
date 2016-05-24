/*
 * 这是QQ服务器，它在监听，等待QQ客户端连接
 * */

package com.qq.server.model;
import java.net.*;
import java.io.*;
import java.util.*;

import org.omg.CORBA.PUBLIC_MEMBER;

import sun.applet.resources.MsgAppletViewer;

import com.qq.common.Message;
import com.qq.common.User;
import com.qq.server.tool.ManagerClientThread;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;
import com.sun.swing.internal.plaf.metal.resources.metal;
public class MyQQServer {
	boolean flag = false;
	static ServerSocket ss;
	
	public MyQQServer() throws ClassNotFoundException     {
		this.flag = flag;
		try {
			this.ss = new ServerSocket(9999);
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try {
			//在9999上监听
			System.out.println("正在监听。。。");
			//阻塞 等待连接
			while(true)
			{
			Socket  s =ss.accept();
			System.out.println("有链接了。。。"+ss);
			//接受客户端信息
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			//System.out.println(ois.readObject());
			
			User u=(User)ois.readObject();
			System.out.println("接收到了消息"+u.getUserid()+" "+u.getPasswd());
			
			Message msg= new Message();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
			if(u.getPasswd().equals("123456"))//测试登录
			{
				//返回一个成功登录的信息报				
				msg.setMesType("1");			//成功
				objectOutputStream.writeObject(msg);
				
				//这里单开一个线程，让该线程与该客户端通信
				SerConClientThread serConClientThread =new SerConClientThread(s);
				//启动该客户端通信线程
				ManagerClientThread.addClientThread(u.getUserid(), serConClientThread);
				serConClientThread.start();
				
//				并通知其他在线用户
				serConClientThread.notifyOther(u.getUserid());
				
			}
			else{
				msg.setMesType("2");			//失败
				objectOutputStream.writeObject(msg);
				//关闭连接，重新监听
				s.close();
			}
				
			}

			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public static void close(){
		try {
			ss.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
