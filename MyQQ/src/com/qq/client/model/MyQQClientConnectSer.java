/*
 * �ͻ������ӷ���������
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

	//���͵�һ������
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
			//���������֤�û���¼�ĵط�
			if(message.getMesType().equals("1"))		//mestype1��ʾ�ɹ� 2��ʾʧ��3��ʾ��ͨ����Ϣ
			{
				//����һ����qq�źͷ������˱���ͨѶ���ӵ��߳�
				ClientConServerThread clientConServerThread = new ClientConServerThread(s);
				//�����߳�
				clientConServerThread.start();
				ManageClientServerThread.addClientConServerThread(u.getUserid(), clientConServerThread);
				b =   true;
			}
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
			return b;
	
	}
	
	public void SendInfoToServer(Object o) {
		
	}
	
	
	
}
