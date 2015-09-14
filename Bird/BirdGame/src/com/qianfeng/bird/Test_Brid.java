package com.qianfeng.bird;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test_Brid extends JPanel {

	private BufferedImage background = null;  
	
	public Test_Brid() throws Exception {
		// TODO Auto-generated constructor stub
		background = ImageIO.read(getClass().getResource("hr.jpg"));
	}
	
	@Override
	public void paint(Graphics g) {
	
	 g.drawImage(background,0,0,null);
	 
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        JFrame frame = new JFrame();
        Test_Brid brid = new Test_Brid();
        
        frame.setSize(440, 670);
        frame.setLocationRelativeTo(null);
		frame.add(brid);
		frame.setVisible(true);
	}

}
