/*
 * 服务器和某个客户端的通信线程
 * */
package com.qq.server.model;
import com.qq.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;



import java.util.Iterator;

import oracle.jrockit.jfr.tools.ConCatRepository;

import com.qq.common.Message;
import com.qq.server.tool.ManagerClientThread;
import com.qq.server.tool.TCPFileRecvServerThread;

public class SerConClientThread extends Thread{
     Socket s;
     boolean flag=true;
    static Message msg;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public SerConClientThread(Socket socket) {
		//把服务端和该客户端的连接赋给s
		this.s=socket;
	}
	public Socket getS() {
		return s;
	}
	//让该线程通知其他用户
	public void notifyOther(String self) throws IOException{
		//得到所有在线的人
		
		HashMap hashMap = ManagerClientThread.hashMap ;
		Iterator it = hashMap.keySet().iterator();
		while (it.hasNext()) {
			Message msg =new Message();
			msg.setContent(self);
			msg.setMesType(MessageType.message_ret_OnLineFriend);
			
			String onLineUserId = it.next().toString();
			ObjectOutputStream objectOutputStream =new  ObjectOutputStream(ManagerClientThread.getClientThread(onLineUserId).s.getOutputStream());//绑定每个在线用户的端口
			msg.setGetter(onLineUserId);
			objectOutputStream.writeObject(msg);
		}
	}
	public void run() {
		while(flag)
		{
			//这里接收客户端的信息
			try {
				ObjectInputStream  objectInputStream =new ObjectInputStream(s.getInputStream());
				 msg = (Message) objectInputStream.readObject();
				System.out.println(msg.getSender()+"发送"+msg.getContent()+"给"+msg.getGetter());
				
				//对消息进行解包
				
				//如果是普通消息就进行转发
				if (msg.getMesType().equals(com.qq.common.MessageType.message_comm_mes)) {
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//取得接收端的socket
					objectOutputStream.writeObject(msg);
				}
				//如果是登录消息就返回在线好友
				else if(msg.getMesType().equals(com.qq.common.MessageType.message_get_OnLineFriend)){
					//把服务器的好友给该客户端返回
					String res= ManagerClientThread.getALLOnLineUserid();
					Message mes = new  Message();
					mes.setMesType(com.qq.common.MessageType.message_ret_OnLineFriend);
					mes.setContent(res);
					mes.setGetter(msg.getSender());
				
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
					objectOutputStream.writeObject(mes);
				}
				//如果是关闭消息就通知其他在线好友
				else if (msg.getMesType().equals(MessageType.message_close)) {
					ManagerClientThread.delClientThread(msg.getSender());
					System.out.println("已经将"+msg.getSender()+"的线程删除了");
					Message mes = new Message();
					String res = ManagerClientThread.getALLOnLineUserid();
					mes.setMesType(com.qq.common.MessageType.message_ret_offLineFriend);
					mes.setContent(res);
					mes.setSender(msg.getSender());
					
						
					//通知所有在线好友  我下线啦
					String onLineFriend[] = res.split(" ");
					for(int i= 0;i<onLineFriend.length;i++){
						mes.setGetter(onLineFriend[i]);
						SerConClientThread serConClientThread = ManagerClientThread.getClientThread(onLineFriend[i]);
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//取得接收端的socket
						objectOutputStream.writeObject(mes);
						System.out.println("通知了"+onLineFriend[i]+"\r\n");
					}					
				}
				else if (msg.getMesType().equals(MessageType.Message_SendFile)) {
					System.out.println("接收到文件消息");
					Message mes = new Message();
					mes.setGetter(msg.getGetter());
					mes.setSender(msg.getSender());
					mes.setMesType(MessageType.Message_RecvFile);
					mes.setContent(msg.getContent());
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//取得接收端的socket
					objectOutputStream.writeObject(mes);									
				}
				else if (msg.getMesType().equals(MessageType.Message_FileRecv_Ready_ack)) {
					Message mesAck = new Message();//返回ack
					System.out.println("对方准备接收");	
					mesAck.setMesType(MessageType.Message_FileRecv_Ready_ack);
					mesAck.setGetter(msg.getGetter());
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//取得接收端的socket
					objectOutputStream.writeObject(mesAck);
					
//					//开启接收文件的线程
					TCPFileRecvServerThread tcpFileRecvServerThread = 
							new TCPFileRecvServerThread(msg.getContent(),msg.getGetter(),msg.getSender());
					tcpFileRecvServerThread.start();
		
				}
				else if (msg.getMesType().equals(MessageType.Message_File_RejectRecv)) {
					Message mesAck = new Message();
					System.out.println("对方拒绝接收文件");
					mesAck.setMesType(MessageType.Message_File_RejectRecv);
					mesAck.setGetter(msg.getGetter());
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//取得接收端的socket
					objectOutputStream.writeObject(mesAck);
				}
				
//				else if (msg.getMesType().equals(MessageType.Message_SendFile)) {
//					System.out.println("接收到文件消息");			
//					Message mesAck = new Message();//返回ack
//					mesAck.setMesType(MessageType.Message_FileRecv_Ready_ack);
//					mesAck.setGetter(msg.getSender());
//					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getSender());
//					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//取得接收端的socket
//					objectOutputStream.writeObject(mesAck);
//				
//					//开启接收文件的线程
//					TCPFileRecvServerThread tcpFileRecvServerThread = 
//							new TCPFileRecvServerThread(msg.getContent(),msg.getSender(),msg.getGetter());
//					tcpFileRecvServerThread.start();
//					
//				}
//				else if (msg.getMesType().equals(MessageType.Message_RecvFile)) {
//					System.out.println("准备转发给对方");
//				}
			
				
			} catch (IOException | ClassNotFoundException e) {
				flag =false;
//				ManagerClientThread.delClientThread(msg.getSender());
//				System.out.println("删除了");
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	}
}
