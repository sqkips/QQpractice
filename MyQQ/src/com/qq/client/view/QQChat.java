/* 
 * 聊天界面
 * 
 * 需要处于一个读取状态，因此要做成一个线程
 * */
package com.qq.client.view;

import com.qq.client.model.MyQQClientConnectSer;
import com.qq.client.tool.JPanel_background;
import com.qq.client.tool.ManageClientServerThread;
import com.qq.client.tool.Read_DEL;
import com.qq.client.tool.TCPFileSendClientThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.qq.client.model.*;
import com.sun.media.jfxmedia.AudioClip;
public class QQChat extends Frame implements ActionListener {
	static JTextArea jTextArea;
	JEditorPane jEditorPane_recv;
	JTextField jTextField;
	JButton jButton_send;
	JButton jButton_close;
	JButton jButton_File;
	JPanel jPanel_banner;
	JPanel_background jPanel_background_banner;
	JPanel jPanel;
	JLabel label_banner;
	JCheckBox j_read_del;
	JScrollPane jsp_chat;
	
	private String owner;	
	private String friend;
	private static String filedir;
	
	
	
	
	public static String getFiledir() {
		return filedir;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		QQChat qqChat = new QQChat("1","2");
		

	}
	public  QQChat(String ownerId ,String friend) {
		this.owner = ownerId;
		this.friend = friend;
		this.filedir = null;
		
		jTextArea = new JTextArea();
		jTextArea.setLineWrap(true );
		jTextArea.setSize(jsp_chat.WIDTH, jsp_chat.HEIGHT);
		jTextArea.setEditable(false);
		jEditorPane_recv = new JEditorPane();
		jEditorPane_recv.setEditable(false);//接收到的聊天信息框
		
		jTextArea.setSize(400, 500);
		jsp_chat = new JScrollPane(jTextArea);		//聊天消息加入到这里
		jsp_chat.setSize(400, 500);
//		jsp_chat.setBounds(10, 50, 50,100);
		jTextField  = new JTextField(30);
	
		jButton_send = new JButton("发送");

		jButton_send.addActionListener(this);
		jButton_close = new JButton("关闭");
		jButton_close.addActionListener(this);
	
		jButton_File =new JButton("传文件");
		jButton_File.addActionListener(this);
		jPanel = new JPanel();	
		jPanel.add(jTextField);
		jPanel.add(jButton_send);
		jPanel.add(jButton_close);
		label_banner =new JLabel();

		j_read_del = new JCheckBox("阅后即焚");
//		if (j_read_del.isSelected()) {
//			read_del_state =true;
//		} else {
//			read_del_state =false;
//		}


		
		
		
		jPanel.add(j_read_del);
		j_read_del.addActionListener(this);
//		jPanel_banner =new JPanel();
		ImageIcon icon = new ImageIcon("image/banner.png");

		jPanel_background_banner =new JPanel_background(icon.getImage());
		jPanel_background_banner.setPreferredSize(new Dimension(50,100));
		
		jPanel_background_banner.add(jButton_File);
		

//		JLabel jLabel_background =new JLabel(icon);
//		jLabel_background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
//		jPanel_banner.add(jLabel_background);
		
		
//		this.add(jPanel_banner, "North");
		this.add(jPanel_background_banner, "North");
		this.add(jsp_chat, "Center");
//		this.add(jEditorPane_recv, "Center");
//		this.add(jTextArea,"Center");
		this.add(jPanel,"South");
		this.setTitle(ownerId+"正在和"+friend+"聊天");
		this.setIconImage((new ImageIcon("image/QQ.png").getImage()));
		this.setSize(600,500);
	
		this.setVisible(true);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == jButton_send)
		{		
			Message msg = new Message();
			msg.setMesType(MessageType.message_comm_mes);
			if(jTextField.getText().isEmpty() ==true)
			{
				
			}
			else{
				if (j_read_del.isSelected()) {
					System.out.println("yes");
					msg.setRead_del(true);				
				} else {
					System.out.println("no");	
					msg.setRead_del(false);
				}	
			msg.setContent( jTextField.getText());
			jTextField.setText("");
			
			msg.setSender(this.owner);
			msg.setGetter(this.friend);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd  hh:mm:ss");
			Date date =new Date();
			msg.setSendTime(dateFormat.format(date));
			String info =msg.getSendTime()+":"+"我"+"("+msg.getGetter()+")"+"\r\n"+" "+msg.getContent()+"\r\n";
			//发送给服务器
			try {
				ObjectOutputStream objectOutputStream =new ObjectOutputStream
						(ManageClientServerThread.getClientConServerThread(this.owner).getSocket().getOutputStream());
				objectOutputStream.writeObject(msg);
				
				jTextArea.append(info);	
//				int line = jTextArea.getLineCount();
//				System.out.println("一共的行数:"+jTextArea.getLineCount());
//				try {
//					System.out.println("最后一行结尾"+jTextArea.getLineEndOffset(line-2));
//					System.out.println("最后一行的开始"+jTextArea.getLineStartOffset(line-2));
//					
//					String[] lineString = jTextArea.getText().split("\n");
//					for(int i=0;i<lineString.length;i++)
//					{
//						System.out.println(i+lineString[i]);
//					}
//					
//				} catch (BadLocationException e1) {
//					// TODO 自动生成的 catch 块
//					e1.printStackTrace();
//				}	
//				try {
//					jTextArea.replaceRange("", jTextArea.getLineStartOffset(line-2), jTextArea.getLineEndOffset(line-2));
//				} catch (BadLocationException e1) {
//					// TODO 自动生成的 catch 块
//					e1.printStackTrace();
//				}
				
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
			
			}

		}
		else if (e.getSource()==jButton_File) {
	        JFileChooser jfc=new JFileChooser();  
	        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
	        jfc.showDialog(new JLabel(), "选择文件");  
	        File file=jfc.getSelectedFile();  
	        if(file.isDirectory()){  
	            System.out.println("文件夹:"+file.getAbsolutePath());  
	        }else 
	        if(file.isFile()){  
	            System.out.println("文件:"+file.getAbsolutePath());  
	
	        }  
	       filedir = file.getAbsolutePath();
	        if (!jfc.getSelectedFile().getName().isEmpty()) {//通知对方有文件需要发送
				Message msg = new Message();
				msg.setMesType(MessageType.Message_SendFile);
				msg.setGetter(friend);
				msg.setSender(owner);
				msg.setContent(file.getName());
				try {
					ObjectOutputStream objectOutputStream = new ObjectOutputStream
							(ManageClientServerThread.getClientConServerThread(owner).getSocket().getOutputStream());
				objectOutputStream.writeObject(msg);
				System.out.println("发送文件的消息已经发出去了");
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

				System.out.println("有文件发送");
				
			}
        
		}
		else if(e.getSource() == jButton_close){

			this.dispose();
		}
		
	}
	
	public void showMessage(Message msg) throws IOException
	{
		if(msg.isRead_del())
		{
			String info =msg.getSendTime()+":"+"对方"+"("+msg.getGetter()+")"+"\r\n"+" "+msg.getContent()+"  "+"("+"注意：此消息5秒后消失！"+")"+"\r\n";
			jTextArea.append(info);
			FileInputStream fileau=new  FileInputStream("sound/msg.wav");
			AudioStream as=new AudioStream(fileau);
			AudioPlayer.player.start(as);			
			int line = jTextArea.getLineCount();
			System.out.println("一共的行数:"+jTextArea.getLineCount());
			try {
				int end = jTextArea.getLineEndOffset(line-2);
				int start = jTextArea.getLineStartOffset(line-2);
						
				System.out.println("最后一行结尾"+end);
				System.out.println("最后一行的开始"+start);
	
				Read_DEL read_DEL = new Read_DEL(line);
				Thread.sleep(5000);
				read_DEL.exe_del(jTextArea);
				
				
			} catch (BadLocationException | InterruptedException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		}
		else {
			String info =msg.getSendTime()+":"+"对方"+"("+msg.getGetter()+")"+"\r\n"+" "+msg.getContent()+"\r\n";
			jTextArea.append(info);
			FileInputStream fileau=new  FileInputStream("sound/msg.wav");
			AudioStream as=new AudioStream(fileau);
			AudioPlayer.player.start(as);	
		}
		
		
	
	}
	
}
