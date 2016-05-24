package com.qq.client.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.qq.client.tool.ManageClientServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

public class ClientCloseThread extends  Thread{

	public ClientCloseThread(String ownerid) throws IOException{
		Message msg = new Message();
		Socket socket =
		ManageClientServerThread.getClientConServerThread(ownerid).getSocket();
		
		msg.setSender(ownerid);
		msg.setMesType(MessageType.message_close);
		ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
		obj.writeObject(msg);
	}

}
