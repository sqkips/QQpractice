package com.qq.server.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPFileSendServerThread  extends Thread{
	private ServerSocket socket ;
	private String sender;
	private String getter;
	private String FileName;
	private boolean flag = true;
	public TCPFileSendServerThread(String FileName){
		this.FileName = FileName;
//		this.sender = sender;
//		this.getter = getter;
	}	
	@Override
	public void run() {
		try {
			socket = new  ServerSocket(9991);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		while(flag){
			try {
				Socket s = socket.accept();
				InputStream inputStream = new FileInputStream("D:/QQ�ļ���תվ/"+FileName);
				
				OutputStream outputStream = s.getOutputStream();
				
				byte [] buffer =  new byte[1024*10];
				int len =0;
				while ((len =inputStream.read(buffer)) !=-1) {
					outputStream.write(buffer,0,len);
				}

				socket.close();
				inputStream.close();
				outputStream.close();
				System.out.println("ת����ɣ�");				
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				flag = false;
				
				e.printStackTrace();
			}
			
			
		} 
		
		
	}
	
}
