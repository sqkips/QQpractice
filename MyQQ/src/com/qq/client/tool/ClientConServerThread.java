/*
 * ���ǿͻ��˺ͷ������˱���ͨ�ŵ��߳�
 * */

package com.qq.client.tool;

import java.io.FileInputStream;

import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



import com.qq.client.view.QQChat;
import com.qq.client.view.QQFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;

public class ClientConServerThread extends Thread {
	private Socket socket;
	boolean flag=true;
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Socket getSocket() {
		return socket;
	}

	public  ClientConServerThread(Socket socket)
	{
		this.socket = socket;
	}
	public void run(){
		
		while(flag)
		{
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) objectInputStream.readObject() ;
//				System.out.println("��ȡ���ӷ���˷�������Ϣ"+msg.getSender()+"��"+msg.getGetter());
				if(msg.getMesType().equals(MessageType.message_comm_mes))
				{
					//�ѷ������л�õ���Ϣ��ʾ��qqchat
					QQChat qqChat =  ManageQQChat.getqqChat(msg.getGetter()+" "+msg.getSender());
					qqChat.showMessage(msg);
				}
				else if(msg.getMesType().equals(MessageType.message_ret_OnLineFriend)){
					System.out.println("���յ��������ߺ���״̬��Ϣ");
					String con =msg.getContent();
					System.out.println(con);				
					String friend[] = con.split(" ");			
					String getter = msg.getGetter();
					System.out.println(getter);				
					//�޸���Ӧ�ĺ����б�
					QQFriendList qqFriendList =ManageQQFriendList.getFriendLIst(getter);
					//�������ߺ���
					if(qqFriendList !=null)
					qqFriendList.OnLineupdateFriendList(msg);				
				}
				else if (msg.getMesType().equals(MessageType.message_ret_offLineFriend)) {
					System.out.println("��֪��"+msg.getSender()+"������");
					String getter = msg.getGetter();
					QQFriendList qqFriendList =ManageQQFriendList.getFriendLIst(getter);
					qqFriendList.OffLineUpdateFriendList(msg);
					
				}
				else if (msg.getMesType().equals(MessageType.Message_FileRecv_Ready_ack)) {
					
					System.out.println("���յ�ack��");
					TCPFileSendClientThread tcpFileSendClientThread = new TCPFileSendClientThread(msg.getSender(),QQChat.getFiledir());
					tcpFileSendClientThread.start();//�����ļ������߳�
					System.out.println("�ļ������߳̿�����");
				}
				else if (msg.getMesType().equals(MessageType.Message_RecvFile)) {
					System.out.println("���յ������ļ�����Ϣ");
					
					int result = JOptionPane.showConfirmDialog(null, 
							"�Ƿ����",msg.getSender()+"���ļ�"+msg.getContent()+"���͸���"+"("+msg.getGetter()+")",  JOptionPane.YES_NO_OPTION);
					if(0==result)
					{
						System.out.println("yes");
						Message mes=new  Message();
						mes.setSender(msg.getGetter());
						mes.setGetter(msg.getSender());
						mes.setContent(msg.getContent());
						mes.setMesType(MessageType.Message_FileRecv_Ready_ack);
						ObjectOutputStream objectOutputStream  = new ObjectOutputStream
								(ManageClientServerThread.getClientConServerThread(msg.getGetter()).getSocket().getOutputStream());
						objectOutputStream.writeObject(mes);
						
					}
					else if (1==result||2==result) {
						System.out.println("no");
						Message mes=new  Message();
						mes.setSender(msg.getGetter());
						mes.setGetter(msg.getSender());
						mes.setContent(msg.getContent());
						mes.setMesType(MessageType.Message_File_RejectRecv);
						ObjectOutputStream objectOutputStream  = new ObjectOutputStream
								(ManageClientServerThread.getClientConServerThread(msg.getGetter()).getSocket().getOutputStream());
						objectOutputStream.writeObject(mes);
					
					}
				}
				else if (msg.getMesType().equals(MessageType.Message_File_RejectRecv)) {
					int result = JOptionPane.showConfirmDialog(null, "�Է���������ļ�","�ǾͲ�����",  JOptionPane.OK_CANCEL_OPTION);
				}
				else if (msg.getMesType().equals(MessageType.Message_File_Forward)) {
					System.out.println("���շ�Ҫ������");
					TCPFileRecvClientThread tcpFileRecvClientThread  = new TCPFileRecvClientThread(msg.getContent());
					tcpFileRecvClientThread.start();
				}
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				
				e.printStackTrace();
			}
			
		}
	}
	
	
}
