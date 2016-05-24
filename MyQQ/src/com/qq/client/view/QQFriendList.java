/**
 * �����б�
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
	//�����б�
	JPanel jPanel1_friend,jPanel2_friend,jPanel3_friend;
	JButton jPanel_friend_jButton1, jPanel_friend_jButton2, jPanel_friend_jButton3;
	JScrollPane jScrollPane1_friend;
	//İ�����б�
	JPanel jPanel1_stranger,jPanel2_stranger,jPanel3_stranger;
	JButton jPanel_stranger_jButton1, jPanel_stranger_jButton2, jPanel_stranger_jButton3;
	JScrollPane jScrollPane1_stranger;
//������JFrame���ó�cardLayout
	CardLayout cl;
	
	//��jPanel2��ʼ��50������
	JLabel [] jLabels = new JLabel[50];
	
	private String owner;
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		//QQFriendList qqFriendList = new QQFriendList(String ownerId);
	}
	
	public void OnLineupdateFriendList(Message msg) throws IOException
	{
		String onLineFriend[] = msg.getContent().split(" ");
		for(int i= 0;i<onLineFriend.length;i++){
			jLabels[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);//���� ���Ѹ���
			FileInputStream fileau=new  FileInputStream("sound/Online.wav");
			AudioStream as=new AudioStream(fileau);
			AudioPlayer.player.start(as);
		}
				
	}
	
	public void OffLineUpdateFriendList(Message msg){
		jLabels[Integer.parseInt(msg.getSender())-1].setEnabled(false);//��������
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
//		enableEvents(AWTEvent.WINDOW_EVENT_MASK);//���մ����¼�
		
		this.owner = ownerId;
		//�����һ���б���ʾ�����б�
		jPanel1_friend =  new JPanel(new BorderLayout());
		jPanel_friend_jButton1 = new JButton("�ҵĺ���");
		jPanel_friend_jButton2 = new JButton("İ����");
		jPanel_friend_jButton2.addActionListener(this);
		jPanel_friend_jButton3 = new JButton("������");
		
		//�ٶ���50������
		jPanel2_friend = new JPanel(new GridLayout(50,1,4,4));
		//��Сͷ��ߴ�
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
		jPanel3_friend = new JPanel(new GridLayout(2,1));//����1��
		
		//��������ť���뵽jPanel3��
		jPanel3_friend.add(jPanel_friend_jButton2);
		jPanel3_friend.add(jPanel_friend_jButton3);
		
		jScrollPane1_friend = new JScrollPane(jPanel2_friend);
		
		jPanel1_friend.add(jPanel_friend_jButton1,"North");
		jPanel1_friend.add(jScrollPane1_friend,"Center");
		jPanel1_friend.add(jPanel3_friend,"South");
		
		//����İ�����б�		
		jPanel_stranger_jButton1 = new JButton("�ҵĺ���");
		jPanel_stranger_jButton1.addActionListener(this);
		jPanel_stranger_jButton2 = new JButton("İ����");
		jPanel_stranger_jButton3 = new JButton("������");
		jPanel1_stranger =  new JPanel(new BorderLayout());
		//�ٶ���20��İ����
		jPanel2_stranger = new JPanel(new GridLayout(20,1,4,4));	
		//��jPanel2��ʼ��20��İ����
		JLabel [] jLabels2 = new JLabel[20];
		for(int i=0;i<jLabels2.length;i++)
		{
			jLabels2[i] = new JLabel(i+1+"",new ImageIcon(small_image),JLabel.LEFT);
			jPanel2_stranger.add(jLabels2[i]);
		}
		jPanel3_stranger = new JPanel(new GridLayout(2,1));//����1��
		
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
		//�ڴ�����ʾ�Լ��ı��
		this.setTitle(this.owner);
		this.setSize(250, 600);
		this.setVisible(true);		//jPanel1(�ҵĺ���)  jPanel2�������� jPanel3İ���˺�������ť
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			   public void windowClosing(WindowEvent e) {
				   int value=JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�ر���");
				    if (value==JOptionPane.OK_OPTION) {
				    	try {
							ClientCloseThread thread = new ClientCloseThread(ownerId);
							thread.start();
						} catch (IOException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
				
				     System.exit(0);
				    }
				   }
			
		});
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource() == jPanel_friend_jButton2)//�����б����İ���˰�ť,��ʾİ�����б�
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
		// TODO �Զ����ɵķ������
		//˫����Ӧ�¼������ߺ��ѱ��
		if(e.getClickCount() == 2)
		{
			//�õ����
			String friendnum = ((JLabel)e.getSource()).getText();
			System.out.println("�㽫��"+friendnum+"����");
			QQChat qqChat =new QQChat(this.owner,friendnum);
			
			//�����������뵽hashmap��
			ManageQQChat.addqqChat(this.owner+" "+friendnum, qqChat);

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JLabel jl = (JLabel)e.getSource();
		jl.setForeground(Color.RED);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JLabel jl = (JLabel)e.getSource();
		jl.setForeground(Color.BLACK);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

}
