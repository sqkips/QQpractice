package com.qq.client.tool;

public class DEL_Thread extends Thread{
	private Read_DEL read_DEL;
	public DEL_Thread(Read_DEL read_DEL)
	{
		this.read_DEL = read_DEL;	
	}	
	@Override
	public void run() {
		try {
			Thread.sleep(5000);				
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

}
