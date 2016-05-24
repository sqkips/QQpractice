package com.qq.client.tool;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class JPanel_background extends javax.swing.JPanel{

	private java.awt.Image image;
	public  JPanel_background(Image image) {
		this.image =image;
        Dimension size = new Dimension(image.getWidth(null),image.getHeight(null));
        setSize(size);
	}
	public void paintComponent(Graphics g) 
	 {
	                  super.paintComponent(g);
	  Dimension size=this.getSize();
	  g.drawImage(image,0,0,size.width,size.height,null);
	 }
	
}
