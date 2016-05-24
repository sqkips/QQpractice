/*
 * 定义包的种类
 * 
 * */

package com.qq.common;

public interface MessageType {
	String message_login_success ="1";		//登录成功包
	String message_login_fail = "2";			//登录失败包
	String message_comm_mes = "3";		//普通消息包
	String message_get_OnLineFriend="4";//获取在线好友包
	String message_ret_OnLineFriend = "5";//返回在线好友包
	String message_close ="6";//关闭客户端信息 
	String message_ret_offLineFriend = "7";//下线好友消息
	String Message_SendFile ="8";//发送文件消息
	String Message_RecvFile ="9";//接收文件消息
	String Message_FileRecv_Ready_ack ="10";//准备接收文件消息
	String Message_File_Forward ="11";//转发文件消息
	String Message_File_RejectRecv ="12";//拒绝接收文件
}
