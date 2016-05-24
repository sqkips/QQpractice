/*
 * QQ登录界面
 * */
package com.qq.client.view;
import javax.swing.*;

import com.qq.client.model.QQClientUser;
import com.qq.client.tool.ManageClientServerThread;
import com.qq.client.tool.ManageQQFriendList;
import com.qq.common.User;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.qq.common.*;
public class QQClientLogin extends JFrame implements ActionListener{

	//定义北部需要的组件
	JLabel jbl1;
	
	
	//定义中部需要的组件
	//中部需要三个panel，由一个选项卡窗口管理
	JTabbedPane jtp;	//下面所有的布局完成后放入jtp中
	
	JPanel jp2,jp3,jp4;
	JLabel jp2_jbl1 ,jp2_jbl2,jp2_jbl3,jp2_jbl4;//QQ帐号密码、忘记密码、申请保护四个label
	JLabel jl_logup;
	JButton jp2_jbl;
	JTextField jp2_JTextField;
	JPasswordField jp2_jPasswordField;
	JCheckBox jp2_jBox1,jp2_jBox2,jp2_jBox3;
	
	//定义南部需要的组件
	JPanel jp1;
	JButton jp1_jb1,jp1_jb2,jp1_jb3;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		QQClientLogin qqClientLogin =new QQClientLogin();

	}
	public QQClientLogin(){
		//处理北边
		ImageIcon icon = new ImageIcon("image/banner.png");
		Image image =icon.getImage();
		Image small_image = image.getScaledInstance(350, 60, SOMEBITS);
		jbl1 =new JLabel(new ImageIcon(small_image));
		
		
		//处理中部
		jp2 =new JPanel(new GridLayout(3,3));//三行三列

		jp2_jbl1 =new JLabel("帐号",JLabel.CENTER);
		
		jp2_jbl2 = new JLabel("密码",JLabel.CENTER);
		jp2_jbl3 = new JLabel("忘记密码",JLabel.CENTER);
		jp2_jbl3.setForeground(Color.BLUE);
//		jp2_jbl4 =new JLabel("申请密码保护");
		jl_logup =new JLabel("注册新帐号",JLabel.CENTER);		
		jl_logup.setForeground(Color.BLUE);
		jp2_JTextField = new JTextField();
		jp2_jPasswordField = new JPasswordField();

		jp2_jBox1 = new JCheckBox("隐身登录");

		jp2_jBox2 = new JCheckBox("记住密码");
		jp2_jBox3 =new JCheckBox("自动登录");
		//把控件按顺序加入到jp2中
		jp2.add(jp2_jbl1);		
		jp2.add(jp2_JTextField);
		jp2.add(jl_logup);
		
		jp2.add(jp2_jbl2);
		jp2.add(jp2_jPasswordField);
		jp2.add(jp2_jbl3);
		jp2.add(jp2_jBox1);
		jp2.add(jp2_jBox2);
		jp2.add(jp2_jBox3);
		
		//创建选项卡窗口
		jtp = new JTabbedPane();
		jtp.add("QQ号码", jp2);
		jp3 = new JPanel();
		jtp.add("手机号码", jp3);
		jp4 = new JPanel();
		jtp.add("电子邮箱", jp4);
		
		
		//处理南部
		jp1 = new JPanel();
		jp1_jb1  = new JButton("登录");
		Dimension preferredSize = new Dimension(60,25);//设置尺寸

		jp1_jb1.setPreferredSize(preferredSize );
//		jp1_jb1.setSize(20,20);
		//响应用户点击登录
		jp1_jb1.addActionListener(this);
		jp1_jb2  = new JButton("设置");
		
		jp1_jb2.setPreferredSize(preferredSize );
		jp1_jb3  = new JButton(new ImageIcon("image/xiangdao.gif"));
		
		//将三个按钮放到jp1
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
//		jp1.add(jp1_jb3);
		
		this.add(jtp,"Center");
		this.add(jbl1,"North");
		this.add(jp1,"South");
		this.setSize(350, 240);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		//如果用户点击的登录了
		if(e.getSource() == jp1_jb1)
		{
			QQClientUser qqUser =new QQClientUser();
			User  u=new User();
			u.setUserid(jp2_JTextField.getText().trim());
			u.setPasswd(new String(jp2_jPasswordField.getPassword() ) );
			try {
				if(qqUser.checkUser(u))//验证登录成功
				{
					QQFriendList  qqFriendList = new QQFriendList(u.getUserid());//进入好友列表
					ManageQQFriendList.addQQFriendList(u.getUserid(), qqFriendList);
					this.dispose();		//关闭登录页面
					
					
					//发送一个要求返回在线好友的请求包
					ObjectOutputStream objectOutputStream =new ObjectOutputStream
							(ManageClientServerThread.getClientConServerThread(u.getUserid()).getSocket().getOutputStream());
					//message					
					Message  msg= new Message();
					msg.setMesType(MessageType.message_get_OnLineFriend);
					msg.setSender(u.getUserid());//指明要求返回好友列表的qq号
					objectOutputStream.writeObject(msg);					
				}else{
					JOptionPane.showMessageDialog(this, "用户名或密码错误！");
				}
			} catch (ClassNotFoundException | IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}

}
