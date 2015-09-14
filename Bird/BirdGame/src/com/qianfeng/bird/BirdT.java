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
   
	private BufferedImage bg;  //背景图片
	private BufferedImage start; //开始图片
	private BufferedImage end;   //结束图片
	
	int score ;
	
	int state;   //游戏状态
	
	public static final int START = 0; 
	public static final int RUNNING =1;
	public static final int GAMEOVER =2;
	
	Bird bird ;
	
	//初始化数据
	public BirdT() throws Exception {
		// TODO Auto-generated constructor stub
	    state = START;
		score = 0; //初始分数 0 分
		//读取背景图片
		bg = ImageIO.read(getClass().getResource("bg.png"));
		start = ImageIO.read(getClass().getResource("start.png"));
		end = ImageIO.read(getClass().getResource("gameover.png"));
		bird = new Bird();
	}
	//画背景
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bg,0,0,null); //画背景
		g.drawImage(start, 0,0,null); //画开始
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40); //字体类
		g.setFont(font);
		g.drawString(score+"", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score+"", 40-3, 60-3);
		g.drawImage(bird.image,bird.x,bird.y,null);
		
		
	}
	//刷新方法
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
         JFrame frame = new JFrame(); //创建窗体对象
         BirdT  bird = new BirdT();
         frame.add(bird);
         frame.setSize(440, 650);
         frame.setVisible(true);
         bird.action();
         
	}
 
	class Bird{
		 BufferedImage image; //小鸟
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
		    width = image.getWidth(); //得到图片的宽度
		    height = image.getHeight();
		    images = new BufferedImage [8] ;
		    for(int i=0;i<8;i++){
		    	//循环体
		    	images[i] = ImageIO.read(getClass().getResource(i+".png"));
		    }
		    
		   
		 }
		 //让小鸟飞
		 public void fly(){
		     index ++;
			 image = images[(index/12)%8];
		 }
	}
	
}
