/*
 * ����QQ�����������ڼ������ȴ�QQ�ͻ�������
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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		try {
			//��9999�ϼ���
			System.out.println("���ڼ���������");
			//���� �ȴ�����
			while(true)
			{
			Socket  s =ss.accept();
			System.out.println("�������ˡ�����"+ss);
			//���ܿͻ�����Ϣ
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			//System.out.println(ois.readObject());
			
			User u=(User)ois.readObject();
			System.out.println("���յ�����Ϣ"+u.getUserid()+" "+u.getPasswd());
			
			Message msg= new Message();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
			if(u.getPasswd().equals("123456"))//���Ե�¼
			{
				//����һ���ɹ���¼����Ϣ��				
				msg.setMesType("1");			//�ɹ�
				objectOutputStream.writeObject(msg);
				
				//���ﵥ��һ���̣߳��ø��߳���ÿͻ���ͨ��
				SerConClientThread serConClientThread =new SerConClientThread(s);
				//�����ÿͻ���ͨ���߳�
				ManagerClientThread.addClientThread(u.getUserid(), serConClientThread);
				serConClientThread.start();
				
//				��֪ͨ���������û�
				serConClientThread.notifyOther(u.getUserid());
				
			}
			else{
				msg.setMesType("2");			//ʧ��
				objectOutputStream.writeObject(msg);
				//�ر����ӣ����¼���
				s.close();
			}
				
			}

			
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public static void close(){
		try {
			ss.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

}
