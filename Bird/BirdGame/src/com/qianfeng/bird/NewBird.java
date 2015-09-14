package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.qianfeng.bird.CrazzyBrid.Brid;

public class NewBird extends JPanel {
    
	BufferedImage background ;
	BufferedImage starImage;
	BufferedImage gameover;
	
	int score = 0;
	
	int state ;                         //游戏状态
	public static final int  STRAT =0;
	public static final int  RUNNING =1;
	public static final int  GAMEOVER =2;
	
	
	private Bird bird ;
	private Ground ground;
	private Column column1;
	private Column column2;
	
	public NewBird() throws Exception {
		// TODO Auto-generated constructor stub
		//获取背景
		background = ImageIO.read(getClass().getResource("bg.png"));
		starImage  = ImageIO.read(getClass().getResource("start.png"));
		gameover   = ImageIO.read(getClass().getResource("gameover.png"));
		
		state = STRAT;
		score = 0;
		
		//初始化类
		bird = new Bird();
		ground = new Ground();
		column1 = new Column(1);
		column2 = new Column(2);
	}
	
	/**
	 * 鼠标监听事件，重绘视图
	 * @throws InterruptedException 
	 * */
	public void action() throws Exception{
		
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//鼠标点击事件
				switch (state) {
				case STRAT:
					state = RUNNING;
					break;

				case RUNNING:
					//调用小鸟向上飞的方法
					bird.flyup();
					break;
				case GAMEOVER:
					try {
						column1 = new Column(1);
						column2 = new Column(2);
						bird = new Bird();
						state = STRAT;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					break;
				}
			}
			
		};
		
		addMouseListener(mouseAdapter);
		//循环绘制
		while (true) {
			switch (state) {
			case STRAT:
				//开始 小鸟飞 地面移动
				bird.fly();
				ground.step();
				break;

		    case RUNNING:
		    	//小鸟飞，地面移动
		    	ground.step();
		    	bird.fly();
		    	bird.step();
		    	column1.step();
		    	column2.step();
		    	//计分器
		    	if(bird.x == column1.x+column1.width/2|| bird.x == column2.x+column2.width/2){
		    		score++;
		    	}
		    	
		       if(bird.hit(ground) || bird.hit(column1) || bird.hit(column2)){
		    	   //撞击地面或者柱子
		    	   
		    	   state= GAMEOVER ;
		    	   
		       }
		    	
		    	break;
			}
			
			repaint();
			Thread.sleep(1000/60);
			
		}
		
		
	}
	
	
	//重写绘图方法
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(background,0,0,null);
		g.drawImage(column1.column, column1.x-column1.width/2, column1.y-column1.height/2, null);
		g.drawImage(column2.column, column2.x-column2.width/2, column2.y-column2.height/2, null);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		g.setFont(font); //设置字体
		g.drawString(score+"", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score+"", 40-3, 60-3);
		g.drawImage(ground.groundImage,ground.x,ground.y,null);
		//画小鸟
        Graphics2D g2d = (Graphics2D)g;
        System.out.println("-bird.alpha :"+-bird.alpha);
        g2d.rotate(-bird.alpha, bird.x, bird.y);
		
		g.drawImage(bird.birdImage,bird.x-bird.width/2,bird.y-bird.height/2,null);
		g2d.rotate(bird.alpha,bird.x, bird.y);
		
		
		switch (state) {
		case STRAT:
			g.drawImage(starImage,0,0,null);
			break;

		case GAMEOVER:
			g.drawImage(gameover,0,0,null);
			break;
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("愤怒的小鸟");
		NewBird bird = new NewBird();
		frame.add(bird);
		frame.setSize(440,670);
		frame.setLocationRelativeTo(null); //放在屏幕中间
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //窗体关闭窗口
		frame.setVisible(true);
		bird.action();
		
	}
	//小鸟类
	class Bird{
		BufferedImage birdImage;
		int x,y;
		int size;
		int width,height;
		BufferedImage [] images ;
		int index ;
		double v0;   //向上速度
		double g ;   //重力加速度
		double s ;   //位移
		double t ;   //间隔时间
		double speeh; //当前速度
		double alpha; //小鸟角度
		
		
		public Bird() throws Exception {
			// TODO Auto-generated constructor stub
			birdImage = ImageIO.read(getClass().getResource("0.png"));
			x = 140;
			y = 248;
			size = 40;
			width = birdImage.getWidth();
			height = birdImage.getHeight();
			index =0;
			
			t= 0.25;   //间隔时间是0.25
			v0 = 20;
			speeh = v0;
			s = 0;
			g = 4;
			
			images = new BufferedImage [8];
			for(int i= 0;i<8;i++){
				images[i]=ImageIO.read(getClass().getResource(i+".png"));
			}
			
		}
	    
		//小鸟飞的方法
		public void fly(){
			index++;
			birdImage = images[(index/12)%8];
		}
		
		public void step(){
			
			double v0 = speeh;
			s = v0*t - g*t*t/2; //计算位移 向上是正数 向下是负数
			y = y-(int)s;       //经多时间后的y值
			speeh = v0-g*t;     //经过时间后的速度
			alpha = Math.atan(s/8);
		}
		
		public void flyup(){
			speeh = v0;
		}
		//撞击地面测试方法
		public boolean hit(Ground ground){
			
			  if(y>= 500){
				  y= 500-size/2;
				  alpha = -Math.PI/2;
				  return true;
			  }
			
			return false;
			
		}
		
		public boolean hit(Column column){
		
			if(x>column.x - column.width/2-size/2 && x<column.x+column.width/2+size/2){
				
				if(y>column.y-column.gap/2+size/2  && y<column.y+column.gap/2-size/2){
					return false;
				}
				
				return true;
			}
			
			return false;
		}
	}
	
	//地面类
	class Ground{
		
		BufferedImage groundImage;
		int x ,y;
		
		public Ground() throws Exception {
			// TODO Auto-generated constructor stub
			groundImage = ImageIO.read(getClass().getResource("ground.png"));
			x = 0;
			y = 500;
		}
		
		public void step(){
			
			x-- ;
			if(x == -71){
				x=0;
			}
			
		}
		
	}
	
	//柱子类
	class Column{
		BufferedImage column;
		int x,y;
		int width,height;
		int gap ;           //柱子的间隙
		int distance; 
		Random random = new Random();  //随机数
		
		public Column(int i) throws Exception {
			// TODO Auto-generated constructor stub
			column = ImageIO.read(getClass().getResource("column.png"));
			width = column.getWidth();
			height =column.getHeight();
			distance = 245;
			gap = 144;
			x = 550+(i-1)*distance;
			y = 132+random.nextInt(218); //上 留 60 下留78
			
		}
		
		public void step(){
			
			x --;  
			if(x == - width/2){
				//两个宽度
				x = 2*distance - width/2;
				y = 132+random.nextInt(218); //上 留 60 下留78
			}
		}
	}
}
