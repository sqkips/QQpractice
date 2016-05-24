/*
 * 该线程用于存储客户端发来的文件，接收到后用来完成转发
 * */

package com.qq.server.tool;

import com.qq.common.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.qq.common.Message;
import com.qq.server.model.SerConClientThread;

public class TCPFileRecvServerThread extends Thread {
	private ServerSocket socket;
	private String sender;
	private String FileName;
	private String getter;
	private boolean flag=true;
	
	public TCPFileRecvServerThread(String FileName,String sender,String getter) {

	this.FileName = FileName;
	this.sender = sender;
	this.getter = getter;
	}
	@Override
	public void run() {
		try {
			socket = new  ServerSocket(9990);
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}	
		while(flag)
		{	
		try {
			Socket  s =socket.accept();
			System.out.println("文件传输端口连接。"+socket);
					
			InputStream inputStream;
			inputStream = s.getInputStream();
			
			OutputStream outputStream = new FileOutputStream("D:/QQ文件中转站/"+FileName);
			System.out.println("开始接收文件");
			byte[] buffer = new byte[1024*10];
			int len=0;
			while((len=inputStream.read(buffer)) !=-1)
			{
				System.out.println("滞留了");
				outputStream.write(buffer,0,len);
			}
			System.out.println("文件接收完毕");
			outputStream.close();
			inputStream.close();
			s.close();
			//服务端转发给接收端，先发消息
			Message msg = new Message();
			msg.setMesType(MessageType.Message_File_Forward);
			msg.setSender(sender);
			msg.setGetter(getter);
			msg.setContent(FileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManagerClientThread.getClientThread(getter).getS().getOutputStream());//取得接收端的socket
			objectOutputStream.writeObject(msg);
			System.out.println("接收完文件，发送消息给接收端接收文件的消息");

			flag = false;
			socket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
			
		}	
		try {
			socket.close();
			
			TCPFileSendServerThread tcpFileSendServerThread = new TCPFileSendServerThread(FileName);
			tcpFileSendServerThread.start();
			
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
