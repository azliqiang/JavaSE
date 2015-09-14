package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BirdT extends JPanel {
   
	private BufferedImage bg;  //����ͼƬ
	private BufferedImage start; //��ʼͼƬ
	private BufferedImage end;   //����ͼƬ
	
	int score ;
	
	int state;   //��Ϸ״̬
	
	public static final int START = 0; 
	public static final int RUNNING =1;
	public static final int GAMEOVER =2;
	
	Bird bird ;
	
	//��ʼ������
	public BirdT() throws Exception {
		// TODO Auto-generated constructor stub
	    state = START;
		score = 0; //��ʼ���� 0 ��
		//��ȡ����ͼƬ
		bg = ImageIO.read(getClass().getResource("bg.png"));
		start = ImageIO.read(getClass().getResource("start.png"));
		end = ImageIO.read(getClass().getResource("gameover.png"));
		bird = new Bird();
	}
	//������
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bg,0,0,null); //������
		g.drawImage(start, 0,0,null); //����ʼ
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40); //������
		g.setFont(font);
		g.drawString(score+"", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score+"", 40-3, 60-3);
		g.drawImage(bird.image,bird.x,bird.y,null);
		
		
	}
	//ˢ�·���
	public void action(){
		while (true) {
			bird.fly();
			repaint();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
         JFrame frame = new JFrame(); //�����������
         BirdT  bird = new BirdT();
         frame.add(bird);
         frame.setSize(440, 650);
         frame.setVisible(true);
         bird.action();
         
	}
 
	class Bird{
		 BufferedImage image; //С��
		 int x ;
		 int y;
		 int width ;
		 int height;
		 BufferedImage [] images ;
		 int index;
		 
		 public Bird() throws Exception {
			// TODO Auto-generated constructor stub
		    image = ImageIO.read(getClass().getResource("0.png"));
		    x = 120;
		    y = 215;
		    index = 0;
		    width = image.getWidth(); //�õ�ͼƬ�Ŀ��
		    height = image.getHeight();
		    images = new BufferedImage [8] ;
		    for(int i=0;i<8;i++){
		    	//ѭ����
		    	images[i] = ImageIO.read(getClass().getResource(i+".png"));
		    }
		    
		   
		 }
		 //��С���
		 public void fly(){
		     index ++;
			 image = images[(index/12)%8];
		 }
	}
	
}
