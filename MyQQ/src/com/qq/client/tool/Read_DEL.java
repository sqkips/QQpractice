package com.qq.client.tool;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Read_DEL {
	private int line;

	public Read_DEL(int line)
	{
		this.line = line;	
	}

	public void exe_del(JTextArea jTextArea){
		try {
			jTextArea.replaceRange("", jTextArea.getLineStartOffset(line-2), jTextArea.getLineEndOffset(line-2));
		} catch (BadLocationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
	
	
}
