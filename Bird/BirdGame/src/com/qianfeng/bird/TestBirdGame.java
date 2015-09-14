package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBirdGame extends JPanel {

	// 背景图片
	private BufferedImage bgImage = null;
	// 开始图片
	private BufferedImage staryImage = null;
	// 结束图片
	private BufferedImage endImage = null;

	// 声明对象
	Brid bird = null;
    // 地面对象
	Ground ground = null;
	
	Column column1= null;
	
	Column column2 = null;
	
	private int state;
	// 游戏运行的三种状态
	public final static int START = 0;
	public final static int RUNNING = 1;
	public final static int GAME_OVER = 2;

	// 分数
	private int score = 0;

	public TestBirdGame() throws Exception {
		// TODO Auto-generated constructor stub

		state = START;

		score = 0;

		bgImage = ImageIO.read(getClass().getResource("bg.png"));
		staryImage = ImageIO.read(getClass().getResource("start.png"));
		endImage = ImageIO.read(getClass().getResource("gameover.png"));

		bird = new Brid();
  
		ground = new Ground();
	
	    column1 = new Column(1);
	    column2 = new Column(2);
		
		
	}

	// 鼠标监听事件和刷新页面方法
	public void action() {

		MouseListener listener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
	
			  switch (state) {
     		case START:
				
     			state = RUNNING;
				break;

			case RUNNING:
			  bird.flyUp();
				
				break;
	   
			case GAME_OVER :
				score = 0;
				state = START;
				try {
					bird = new Brid();
					column1 = new Column(1);
					column2 = new Column(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				break;
			  
			   }
			
			}
		};

		this.addMouseListener(listener);
		// 循环刷新
		while (true) {

			switch (state) {
			case START:
				// 开始
				bird.fly();
				ground.step();
				break;

			case RUNNING:
				// 游戏运行状态
				bird.fly();
				ground.step();
				column1.step();
				column2.step();
				bird.step();
				
				if(bird.hit(column1)||bird.hit(column2)||bird.hit(ground)){
					state =GAME_OVER;
				}
				if(bird.x == column1.x || bird.x ==column2.x){
					score++;
				}
				
				break;
			case GAME_OVER:
				// 游戏结束状态

				break;
			}

			repaint();
			
			
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	// 重写画笔方法
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(bgImage, 0, 0, null);
        
		Graphics2D graphics2d = (Graphics2D)g;
      graphics2d.rotate(-bird.rad,bird.x - bird.width / 2, bird.y - bird.height
				/ 2);
		g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height
				/ 2, null);
		   graphics2d.rotate(bird.rad,bird.x - bird.width / 2, bird.y - bird.height
					/ 2);
        System.out.println(column1.x+"::"+column2.x);
		g.drawImage(column1.image, column1.x-column1.width/2, column1.y-column1.height/2, null);
		g.drawImage(column2.image, column2.x-column2.width/2, column2.y-column2.height/2, null);
		
		g.drawImage(ground.image, ground.x, ground.y, null);
		
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		g.setFont(font);
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score + "", 40 - 3, 60 - 3);
		
		switch (state) {
		case START:

			g.drawImage(staryImage, 0, 0, null);

			break;

		case GAME_OVER:
			g.drawImage(endImage, 0, 0, null);
			break;
		}

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
  
		JFrame frame = new JFrame();

		TestBirdGame birdGame = new TestBirdGame();

		frame.setSize(440, 670);

		frame.add(birdGame);
		// 位于屏幕的中间
		frame.setLocationRelativeTo(null);
		// 点击关闭窗体
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		birdGame.action();

	}

	public class Brid {

		private BufferedImage image = null;
		private int x, y;
		private int width;
		private int height;
		private int size;
		private int index = 0;

		private BufferedImage[] images = null;

		//小鸟向上飞的属性
		
		double g = 0; //重力加速度
		double v0 = 0; //初速度
		double time = 0; //时间
		double s = 0; //距离
		double speeh = 0;//当前速度
		
		double rad = 0;
		
		
		public Brid() throws IOException {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();
			x = 134;
			y = 234;
			size = 40;

			g = 4;
			v0 =20;
			time = 0.25;
			speeh = v0;
			
			index = 0;
			images = new BufferedImage[8];
    
			rad = 0;
			
			for (int i = 0; i < 8; i++) {

				images[i] = ImageIO.read(getClass().getResource(i + ".png"));
			}

		}

		public void fly() {
			index++;
			image = images[index/10 % 8];

		}

		public void step(){
			
			double v  = speeh;
			s = v*time - g*time*time/2;
			y = y - (int)s;
			speeh = v - g*time;
			rad = Math.atan(s/8);
			
		}
		
		
		public void flyUp(){
			speeh = v0;
			
		}
		
		
		public boolean hit(Ground ground){
			
			if(ground.y<=y+height/2){
				
				y = 500-height/2;
				return true;
			}
			
			return false;
		}
		
		public boolean hit(Column column){
			
			if(x>column.x-column.width/2-size/2&&x<column.x+column.width/2+size/2){
				
				if(y>column.y-column.gap/2+size/2&&y<column.y+column.gap/2-size/2){
					
					return false;
				}
				
				return true;
			}
			
			 return false;
			
		}
		
	}
  
	public class Ground{
		
		private BufferedImage image ;
		private int x ,y;
		
		public Ground() throws IOException {
          image = ImageIO.read(getClass().getResource("ground.png"));	
          x = 0;
          y=500;
 		}
		
       public void step(){
    	   x--;
    	   if(x==-109){
    		   x= 0;
    	   }
    	   
       }
		
	
	}
	
	
	
	public class Column{
		
	 private BufferedImage image = null;
	 private int x,y;
	 private int distance = 0 ;
	 private int width = 0;
	 private int height = 0;
	 private int gap = 0;
	 Random random = new Random();
	 
	 public Column(int n) throws IOException{
		// TODO Auto-generated constructor stub
	
	  image = ImageIO.read(getClass().getResource("column.png"));	 
	  width = image.getWidth();
	  height = image.getHeight();
	  gap = 144;
	  distance = 245;
	  y = random.nextInt(238)+132;
	  x = 550+(n-1)*distance;
	  }
	 
 	 public void step(){
 		x--;
 		if(x == -width/2){
 			x = 2*distance-width/2;
 			y = random.nextInt(238)+132;
 			
 		}
 		 
 	 } 
	}
	
	
	
}
