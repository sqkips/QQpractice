/*
 * �����������
 * 
 * */

package com.qq.common;

public interface MessageType {
	String message_login_success ="1";		//��¼�ɹ���
	String message_login_fail = "2";			//��¼ʧ�ܰ�
	String message_comm_mes = "3";		//��ͨ��Ϣ��
	String message_get_OnLineFriend="4";//��ȡ���ߺ��Ѱ�
	String message_ret_OnLineFriend = "5";//�������ߺ��Ѱ�
	String message_close ="6";//�رտͻ�����Ϣ 
	String message_ret_offLineFriend = "7";//���ߺ�����Ϣ
	String Message_SendFile ="8";//�����ļ���Ϣ
	String Message_RecvFile ="9";//�����ļ���Ϣ
	String Message_FileRecv_Ready_ack ="10";//׼�������ļ���Ϣ
	String Message_File_Forward ="11";//ת���ļ���Ϣ
	String Message_File_RejectRecv ="12";//�ܾ������ļ�
}
