/*
 * ���߳����ڴ洢�ͻ��˷������ļ������յ����������ת��
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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}	
		while(flag)
		{	
		try {
			Socket  s =socket.accept();
			System.out.println("�ļ�����˿����ӡ�"+socket);
					
			InputStream inputStream;
			inputStream = s.getInputStream();
			
			OutputStream outputStream = new FileOutputStream("D:/QQ�ļ���תվ/"+FileName);
			System.out.println("��ʼ�����ļ�");
			byte[] buffer = new byte[1024*10];
			int len=0;
			while((len=inputStream.read(buffer)) !=-1)
			{
				System.out.println("������");
				outputStream.write(buffer,0,len);
			}
			System.out.println("�ļ��������");
			outputStream.close();
			inputStream.close();
			s.close();
			//�����ת�������նˣ��ȷ���Ϣ
			Message msg = new Message();
			msg.setMesType(MessageType.Message_File_Forward);
			msg.setSender(sender);
			msg.setGetter(getter);
			msg.setContent(FileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManagerClientThread.getClientThread(getter).getS().getOutputStream());//ȡ�ý��ն˵�socket
			objectOutputStream.writeObject(msg);
			System.out.println("�������ļ���������Ϣ�����ն˽����ļ�����Ϣ");

			flag = false;
			socket.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
			
		}	
		try {
			socket.close();
			
			TCPFileSendServerThread tcpFileSendServerThread = new TCPFileSendServerThread(FileName);
			tcpFileSendServerThread.start();
			
			
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
}
