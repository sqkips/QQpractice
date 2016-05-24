/*
 * QQ��¼����
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

	//���山����Ҫ�����
	JLabel jbl1;
	
	
	//�����в���Ҫ�����
	//�в���Ҫ����panel����һ��ѡ����ڹ���
	JTabbedPane jtp;	//�������еĲ�����ɺ����jtp��
	
	JPanel jp2,jp3,jp4;
	JLabel jp2_jbl1 ,jp2_jbl2,jp2_jbl3,jp2_jbl4;//QQ�ʺ����롢�������롢���뱣���ĸ�label
	JLabel jl_logup;
	JButton jp2_jbl;
	JTextField jp2_JTextField;
	JPasswordField jp2_jPasswordField;
	JCheckBox jp2_jBox1,jp2_jBox2,jp2_jBox3;
	
	//�����ϲ���Ҫ�����
	JPanel jp1;
	JButton jp1_jb1,jp1_jb2,jp1_jb3;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		QQClientLogin qqClientLogin =new QQClientLogin();

	}
	public QQClientLogin(){
		//������
		ImageIcon icon = new ImageIcon("image/banner.png");
		Image image =icon.getImage();
		Image small_image = image.getScaledInstance(350, 60, SOMEBITS);
		jbl1 =new JLabel(new ImageIcon(small_image));
		
		
		//�����в�
		jp2 =new JPanel(new GridLayout(3,3));//��������

		jp2_jbl1 =new JLabel("�ʺ�",JLabel.CENTER);
		
		jp2_jbl2 = new JLabel("����",JLabel.CENTER);
		jp2_jbl3 = new JLabel("��������",JLabel.CENTER);
		jp2_jbl3.setForeground(Color.BLUE);
//		jp2_jbl4 =new JLabel("�������뱣��");
		jl_logup =new JLabel("ע�����ʺ�",JLabel.CENTER);		
		jl_logup.setForeground(Color.BLUE);
		jp2_JTextField = new JTextField();
		jp2_jPasswordField = new JPasswordField();

		jp2_jBox1 = new JCheckBox("�����¼");

		jp2_jBox2 = new JCheckBox("��ס����");
		jp2_jBox3 =new JCheckBox("�Զ���¼");
		//�ѿؼ���˳����뵽jp2��
		jp2.add(jp2_jbl1);		
		jp2.add(jp2_JTextField);
		jp2.add(jl_logup);
		
		jp2.add(jp2_jbl2);
		jp2.add(jp2_jPasswordField);
		jp2.add(jp2_jbl3);
		jp2.add(jp2_jBox1);
		jp2.add(jp2_jBox2);
		jp2.add(jp2_jBox3);
		
		//����ѡ�����
		jtp = new JTabbedPane();
		jtp.add("QQ����", jp2);
		jp3 = new JPanel();
		jtp.add("�ֻ�����", jp3);
		jp4 = new JPanel();
		jtp.add("��������", jp4);
		
		
		//�����ϲ�
		jp1 = new JPanel();
		jp1_jb1  = new JButton("��¼");
		Dimension preferredSize = new Dimension(60,25);//���óߴ�

		jp1_jb1.setPreferredSize(preferredSize );
//		jp1_jb1.setSize(20,20);
		//��Ӧ�û������¼
		jp1_jb1.addActionListener(this);
		jp1_jb2  = new JButton("����");
		
		jp1_jb2.setPreferredSize(preferredSize );
		jp1_jb3  = new JButton(new ImageIcon("image/xiangdao.gif"));
		
		//��������ť�ŵ�jp1
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
		// TODO �Զ����ɵķ������
		//����û�����ĵ�¼��
		if(e.getSource() == jp1_jb1)
		{
			QQClientUser qqUser =new QQClientUser();
			User  u=new User();
			u.setUserid(jp2_JTextField.getText().trim());
			u.setPasswd(new String(jp2_jPasswordField.getPassword() ) );
			try {
				if(qqUser.checkUser(u))//��֤��¼�ɹ�
				{
					QQFriendList  qqFriendList = new QQFriendList(u.getUserid());//��������б�
					ManageQQFriendList.addQQFriendList(u.getUserid(), qqFriendList);
					this.dispose();		//�رյ�¼ҳ��
					
					
					//����һ��Ҫ�󷵻����ߺ��ѵ������
					ObjectOutputStream objectOutputStream =new ObjectOutputStream
							(ManageClientServerThread.getClientConServerThread(u.getUserid()).getSocket().getOutputStream());
					//message					
					Message  msg= new Message();
					msg.setMesType(MessageType.message_get_OnLineFriend);
					msg.setSender(u.getUserid());//ָ��Ҫ�󷵻غ����б��qq��
					objectOutputStream.writeObject(msg);					
				}else{
					JOptionPane.showMessageDialog(this, "�û������������");
				}
			} catch (ClassNotFoundException | IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
	}

}
