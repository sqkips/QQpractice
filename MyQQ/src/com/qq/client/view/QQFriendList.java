/**
 * 好友列表
 */
package com.qq.client.view;

import javax.swing.ImageIcon;

import com.qq.client.model.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.omg.CORBA.PUBLIC_MEMBER;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.qq.client.tool.ManageQQChat;
import com.qq.common.Message;
import com.qq.client.tool.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class QQFriendList extends JFrame implements ActionListener,MouseListener{
	//好友列表
	JPanel jPanel1_friend,jPanel2_friend,jPanel3_friend;
	JButton jPanel_friend_jButton1, jPanel_friend_jButton2, jPanel_friend_jButton3;
	JScrollPane jScrollPane1_friend;
	//陌生人列表
	JPanel jPanel1_stranger,jPanel2_stranger,jPanel3_stranger;
	JButton jPanel_stranger_jButton1, jPanel_stranger_jButton2, jPanel_stranger_jButton3;
	JScrollPane jScrollPane1_stranger;
//将整个JFrame设置成cardLayout
	CardLayout cl;
	
	//给jPanel2初始化50个好友
	JLabel [] jLabels = new JLabel[50];
	
	private String owner;
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//QQFriendList qqFriendList = new QQFriendList(String ownerId);
	}
	
	public void OnLineupdateFriendList(Message msg) throws IOException
	{
		String onLineFriend[] = msg.getContent().split(" ");
		for(int i= 0;i<onLineFriend.length;i++){
			jLabels[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);//在线 好友更新
			FileInputStream fileau=new  FileInputStream("sound/Online.wav");
			AudioStream as=new AudioStream(fileau);
			AudioPlayer.player.start(as);
		}
				
	}
	
	public void OffLineUpdateFriendList(Message msg){
		jLabels[Integer.parseInt(msg.getSender())-1].setEnabled(false);//好友下线
	}
//	protected void pocessWindowsEvent(final WindowEvent pEvent){
//		if(pEvent.getID() == WindowEvent.WINDOW_CLOSED)
//		{
//			return ;
//		}
//		else {
//			super.processWindowEvent(pEvent);
//		}
//	}
	
	public  QQFriendList(String ownerId){
//		enableEvents(AWTEvent.WINDOW_EVENT_MASK);//接收窗口事件
		
		this.owner = ownerId;
		//处理第一个列表（显示好友列表）
		jPanel1_friend =  new JPanel(new BorderLayout());
		jPanel_friend_jButton1 = new JButton("我的好友");
		jPanel_friend_jButton2 = new JButton("陌生人");
		jPanel_friend_jButton2.addActionListener(this);
		jPanel_friend_jButton3 = new JButton("黑名单");
		
		//假定有50个好友
		jPanel2_friend = new JPanel(new GridLayout(50,1,4,4));
		//缩小头像尺寸
		ImageIcon icon = new ImageIcon("image/QQ.png");
		Image image =icon.getImage();
		Image small_image = image.getScaledInstance(20, 20, SOMEBITS);
		
		

		for(int i=0;i<jLabels.length;i++)
		{
			jLabels[i] = new JLabel(i+1+"",new ImageIcon(small_image),JLabel.LEFT);
			jLabels[i].addMouseListener(this);
			jLabels[i].setEnabled(false);
			if(jLabels[i].getText().equals(ownerId))
			{
				jLabels[i].setEnabled(true);
			}
			jPanel2_friend.add(jLabels[i]);
		}
		jPanel3_friend = new JPanel(new GridLayout(2,1));//两行1列
		
		//把两个按钮加入到jPanel3中
		jPanel3_friend.add(jPanel_friend_jButton2);
		jPanel3_friend.add(jPanel_friend_jButton3);
		
		jScrollPane1_friend = new JScrollPane(jPanel2_friend);
		
		jPanel1_friend.add(jPanel_friend_jButton1,"North");
		jPanel1_friend.add(jScrollPane1_friend,"Center");
		jPanel1_friend.add(jPanel3_friend,"South");
		
		//处理陌生人列表		
		jPanel_stranger_jButton1 = new JButton("我的好友");
		jPanel_stranger_jButton1.addActionListener(this);
		jPanel_stranger_jButton2 = new JButton("陌生人");
		jPanel_stranger_jButton3 = new JButton("黑名单");
		jPanel1_stranger =  new JPanel(new BorderLayout());
		//假定有20个陌生人
		jPanel2_stranger = new JPanel(new GridLayout(20,1,4,4));	
		//给jPanel2初始化20个陌生人
		JLabel [] jLabels2 = new JLabel[20];
		for(int i=0;i<jLabels2.length;i++)
		{
			jLabels2[i] = new JLabel(i+1+"",new ImageIcon(small_image),JLabel.LEFT);
			jPanel2_stranger.add(jLabels2[i]);
		}
		jPanel3_stranger = new JPanel(new GridLayout(2,1));//两行1列
		
		jPanel3_stranger.add(jPanel_stranger_jButton1);
		jPanel3_stranger.add(jPanel_stranger_jButton2);
		
		jScrollPane1_stranger = new JScrollPane(jPanel2_stranger);
		
		jPanel1_stranger.add(jPanel3_stranger,"North");
		jPanel1_stranger.add(jScrollPane1_stranger,"Center");
		jPanel1_stranger.add(jPanel_stranger_jButton3,"South");
		

		cl =new CardLayout();
		this.setLayout(cl);
		this.add(jPanel1_friend, "1");
		this.add(jPanel1_stranger, "2");
		//在窗口显示自己的编号
		this.setTitle(this.owner);
		this.setSize(250, 600);
		this.setVisible(true);		//jPanel1(我的好友)  jPanel2滚动部分 jPanel3陌生人黑名单按钮
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			   public void windowClosing(WindowEvent e) {
				   int value=JOptionPane.showConfirmDialog(null, "确定要关闭吗？");
				    if (value==JOptionPane.OK_OPTION) {
				    	try {
							ClientCloseThread thread = new ClientCloseThread(ownerId);
							thread.start();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
				
				     System.exit(0);
				    }
				   }
			
		});
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == jPanel_friend_jButton2)//好友列表里的陌生人按钮,显示陌生人列表
		{
			cl.show(this.getContentPane(), "2");
		}
		else if(e.getSource() == jPanel_stranger_jButton1) 
		{
			cl.show(this.getContentPane(), "1");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		//双击响应事件，或者好友编号
		if(e.getClickCount() == 2)
		{
			//得到编号
			String friendnum = ((JLabel)e.getSource()).getText();
			System.out.println("你将和"+friendnum+"聊天");
			QQChat qqChat =new QQChat(this.owner,friendnum);
			
			//把聊天界面放入到hashmap中
			ManageQQChat.addqqChat(this.owner+" "+friendnum, qqChat);

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		JLabel jl = (JLabel)e.getSource();
		jl.setForeground(Color.RED);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JLabel jl = (JLabel)e.getSource();
		jl.setForeground(Color.BLACK);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

}
