package com.qq.client.model;

import com.qq.common.*;

public class QQClientUser {

	public boolean checkUser(User u) throws ClassNotFoundException
	{
		
		return new MyQQClientConnectSer().SendLoginInfoToServer(u);
	}
}
