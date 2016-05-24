package com.qq.client.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.qq.common.Message;
import com.qq.common.MessageType;

public class TCPFileSendClientThread extends Thread{
	private Socket socket;
	private String owner;
	private String filedir;

	public Socket getSocket() {
		return socket;
	}
	public TCPFileSendClientThread(String owner,String filedir){
		this.owner = owner;
		this.filedir = filedir;
		
	}
	public void run()
	{		

		InputStream in;
		try {
			
			socket = new Socket("10.95.71.90",9990);
					
			String new_filedir = filedir.replaceAll("\\\\", "/");
			in = new FileInputStream(new_filedir);	
			System.out.println(new_filedir);
			BufferedInputStream bufferedInputStream =new BufferedInputStream(in);
		
			OutputStream  out =socket.getOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
			byte[] buffer = new byte[1024*10];
			int len=0;
			while((len=bufferedInputStream.read(buffer)) !=-1)
			{

				bufferedOutputStream.write(buffer,0,len);
			}
			System.out.println("文件发出了");
		in.close();
		bufferedInputStream.close();
		bufferedOutputStream.close();
		socket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
	
}
