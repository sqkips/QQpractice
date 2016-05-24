/*
 * 
 * 服务器控制界面，可以完成启动服务器，关闭服务器
 * */
package com.qq.server.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.qq.server.model.MyQQServer;

public class MyServerFrame extends	JFrame implements ActionListener,Runnable{
	JPanel jPanel1;
	JButton jButton1,jButton2;
	
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		MyServerFrame myServerFrame = new MyServerFrame();
		
		Thread thread = new Thread(myServerFrame);
		thread.start();
	}
	
	public  MyServerFrame() {
		jPanel1 = new JPanel();
//		jButton1 = new JButton("启动服务器");
//		jButton1.addActionListener(this);
		jButton2 = new JButton("关闭服务器");
		jButton2.addActionListener(this);
//		jPanel1.add(jButton1);
		jPanel1.add(jButton2);
		
		this.add(jPanel1);
		this.setSize(500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed  (ActionEvent arg0){
		// TODO 自动生成的方法存根
		 if(arg0.getSource()==jButton2){ 
			MyQQServer.close();
			this.dispose();
		}
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根

		try {
			new MyQQServer ();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	

}
