/*
 * ��������ĳ���ͻ��˵�ͨ���߳�
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
		//�ѷ���˺͸ÿͻ��˵����Ӹ���s
		this.s=socket;
	}
	public Socket getS() {
		return s;
	}
	//�ø��߳�֪ͨ�����û�
	public void notifyOther(String self) throws IOException{
		//�õ��������ߵ���
		
		HashMap hashMap = ManagerClientThread.hashMap ;
		Iterator it = hashMap.keySet().iterator();
		while (it.hasNext()) {
			Message msg =new Message();
			msg.setContent(self);
			msg.setMesType(MessageType.message_ret_OnLineFriend);
			
			String onLineUserId = it.next().toString();
			ObjectOutputStream objectOutputStream =new  ObjectOutputStream(ManagerClientThread.getClientThread(onLineUserId).s.getOutputStream());//��ÿ�������û��Ķ˿�
			msg.setGetter(onLineUserId);
			objectOutputStream.writeObject(msg);
		}
	}
	public void run() {
		while(flag)
		{
			//������տͻ��˵���Ϣ
			try {
				ObjectInputStream  objectInputStream =new ObjectInputStream(s.getInputStream());
				 msg = (Message) objectInputStream.readObject();
				System.out.println(msg.getSender()+"����"+msg.getContent()+"��"+msg.getGetter());
				
				//����Ϣ���н��
				
				//�������ͨ��Ϣ�ͽ���ת��
				if (msg.getMesType().equals(com.qq.common.MessageType.message_comm_mes)) {
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//ȡ�ý��ն˵�socket
					objectOutputStream.writeObject(msg);
				}
				//����ǵ�¼��Ϣ�ͷ������ߺ���
				else if(msg.getMesType().equals(com.qq.common.MessageType.message_get_OnLineFriend)){
					//�ѷ������ĺ��Ѹ��ÿͻ��˷���
					String res= ManagerClientThread.getALLOnLineUserid();
					Message mes = new  Message();
					mes.setMesType(com.qq.common.MessageType.message_ret_OnLineFriend);
					mes.setContent(res);
					mes.setGetter(msg.getSender());
				
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
					objectOutputStream.writeObject(mes);
				}
				//����ǹر���Ϣ��֪ͨ�������ߺ���
				else if (msg.getMesType().equals(MessageType.message_close)) {
					ManagerClientThread.delClientThread(msg.getSender());
					System.out.println("�Ѿ���"+msg.getSender()+"���߳�ɾ����");
					Message mes = new Message();
					String res = ManagerClientThread.getALLOnLineUserid();
					mes.setMesType(com.qq.common.MessageType.message_ret_offLineFriend);
					mes.setContent(res);
					mes.setSender(msg.getSender());
					
						
					//֪ͨ�������ߺ���  ��������
					String onLineFriend[] = res.split(" ");
					for(int i= 0;i<onLineFriend.length;i++){
						mes.setGetter(onLineFriend[i]);
						SerConClientThread serConClientThread = ManagerClientThread.getClientThread(onLineFriend[i]);
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//ȡ�ý��ն˵�socket
						objectOutputStream.writeObject(mes);
						System.out.println("֪ͨ��"+onLineFriend[i]+"\r\n");
					}					
				}
				else if (msg.getMesType().equals(MessageType.Message_SendFile)) {
					System.out.println("���յ��ļ���Ϣ");
					Message mes = new Message();
					mes.setGetter(msg.getGetter());
					mes.setSender(msg.getSender());
					mes.setMesType(MessageType.Message_RecvFile);
					mes.setContent(msg.getContent());
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//ȡ�ý��ն˵�socket
					objectOutputStream.writeObject(mes);									
				}
				else if (msg.getMesType().equals(MessageType.Message_FileRecv_Ready_ack)) {
					Message mesAck = new Message();//����ack
					System.out.println("�Է�׼������");	
					mesAck.setMesType(MessageType.Message_FileRecv_Ready_ack);
					mesAck.setGetter(msg.getGetter());
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//ȡ�ý��ն˵�socket
					objectOutputStream.writeObject(mesAck);
					
//					//���������ļ����߳�
					TCPFileRecvServerThread tcpFileRecvServerThread = 
							new TCPFileRecvServerThread(msg.getContent(),msg.getGetter(),msg.getSender());
					tcpFileRecvServerThread.start();
		
				}
				else if (msg.getMesType().equals(MessageType.Message_File_RejectRecv)) {
					Message mesAck = new Message();
					System.out.println("�Է��ܾ������ļ�");
					mesAck.setMesType(MessageType.Message_File_RejectRecv);
					mesAck.setGetter(msg.getGetter());
					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getGetter());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//ȡ�ý��ն˵�socket
					objectOutputStream.writeObject(mesAck);
				}
				
//				else if (msg.getMesType().equals(MessageType.Message_SendFile)) {
//					System.out.println("���յ��ļ���Ϣ");			
//					Message mesAck = new Message();//����ack
//					mesAck.setMesType(MessageType.Message_FileRecv_Ready_ack);
//					mesAck.setGetter(msg.getSender());
//					SerConClientThread serConClientThread = ManagerClientThread.getClientThread(msg.getSender());
//					ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.s.getOutputStream());//ȡ�ý��ն˵�socket
//					objectOutputStream.writeObject(mesAck);
//				
//					//���������ļ����߳�
//					TCPFileRecvServerThread tcpFileRecvServerThread = 
//							new TCPFileRecvServerThread(msg.getContent(),msg.getSender(),msg.getGetter());
//					tcpFileRecvServerThread.start();
//					
//				}
//				else if (msg.getMesType().equals(MessageType.Message_RecvFile)) {
//					System.out.println("׼��ת�����Է�");
//				}
			
				
			} catch (IOException | ClassNotFoundException e) {
				flag =false;
//				ManagerClientThread.delClientThread(msg.getSender());
//				System.out.println("ɾ����");
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}
		
	}
}
