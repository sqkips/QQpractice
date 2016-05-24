package com.qq.client.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class TCPFileRecvClientThread extends Thread {
	private Socket socket;
	private String  FileName;
	public TCPFileRecvClientThread(String FileName)
	{
		this.FileName = FileName;
	}
	@Override
	public void run() {
		OutputStream outputStream;	
		try {
			socket = new Socket("10.95.71.90",9991);
			InputStream  in  = socket.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
			outputStream = new FileOutputStream(FileName);
			BufferedOutputStream bufferedOutputStream  = new BufferedOutputStream(outputStream);
			byte [] buffer = new byte[1024*10];
			int len=0;
			while((len = bufferedInputStream.read(buffer))!=-1){
				bufferedOutputStream.write(buffer, 0, len);
			}
			bufferedInputStream.close();
			bufferedOutputStream.close();
			socket.close();		
			int result=JOptionPane.showConfirmDialog(null, "接收完成，是否打开所在目录？","接收完成",JOptionPane.YES_NO_CANCEL_OPTION);
			if (result ==0) {
				String  FILE_PATH = "C:/Users/Administrator/Desktop/MyQQ";
				Runtime.getRuntime().exec("explorer.exe C:\\Users\\Administrator\\Desktop\\MyQQ");
//				Runtime.getRuntime().exec(new String[] {"cmd","/c","start","",FILE_PATH});
			} 

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
}
