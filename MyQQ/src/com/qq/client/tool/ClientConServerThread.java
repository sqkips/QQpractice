/*
 * 这是客户端和服务器端保持通信的线程
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
//				System.out.println("读取到从服务端发来的消息"+msg.getSender()+"给"+msg.getGetter());
				if(msg.getMesType().equals(MessageType.message_comm_mes))
				{
					//把服务器中获得的消息显示到qqchat
					QQChat qqChat =  ManageQQChat.getqqChat(msg.getGetter()+" "+msg.getSender());
					qqChat.showMessage(msg);
				}
				else if(msg.getMesType().equals(MessageType.message_ret_OnLineFriend)){
					System.out.println("接收到返回在线好友状态消息");
					String con =msg.getContent();
					System.out.println(con);				
					String friend[] = con.split(" ");			
					String getter = msg.getGetter();
					System.out.println(getter);				
					//修改相应的好友列表
					QQFriendList qqFriendList =ManageQQFriendList.getFriendLIst(getter);
					//更新在线好友
					if(qqFriendList !=null)
					qqFriendList.OnLineupdateFriendList(msg);				
				}
				else if (msg.getMesType().equals(MessageType.message_ret_offLineFriend)) {
					System.out.println("我知道"+msg.getSender()+"下线了");
					String getter = msg.getGetter();
					QQFriendList qqFriendList =ManageQQFriendList.getFriendLIst(getter);
					qqFriendList.OffLineUpdateFriendList(msg);
					
				}
				else if (msg.getMesType().equals(MessageType.Message_FileRecv_Ready_ack)) {
					
					System.out.println("接收到ack了");
					TCPFileSendClientThread tcpFileSendClientThread = new TCPFileSendClientThread(msg.getSender(),QQChat.getFiledir());
					tcpFileSendClientThread.start();//开启文件发送线程
					System.out.println("文件发送线程开启了");
				}
				else if (msg.getMesType().equals(MessageType.Message_RecvFile)) {
					System.out.println("接收到接收文件的消息");
					
					int result = JOptionPane.showConfirmDialog(null, 
							"是否接受",msg.getSender()+"有文件"+msg.getContent()+"发送给您"+"("+msg.getGetter()+")",  JOptionPane.YES_NO_OPTION);
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
					int result = JOptionPane.showConfirmDialog(null, "对方不想接收文件","那就不发了",  JOptionPane.OK_CANCEL_OPTION);
				}
				else if (msg.getMesType().equals(MessageType.Message_File_Forward)) {
					System.out.println("接收方要接收了");
					TCPFileRecvClientThread tcpFileRecvClientThread  = new TCPFileRecvClientThread(msg.getContent());
					tcpFileRecvClientThread.start();
				}
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				
				e.printStackTrace();
			}
			
		}
	}
	
	
}
