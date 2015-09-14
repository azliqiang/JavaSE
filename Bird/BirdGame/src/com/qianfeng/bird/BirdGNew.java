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

/**
 * 1.创建画布类 2.重写main方法
 * */

public class BirdGNew extends JPanel {

	public int state; // 游戏状态
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 2;

	public BufferedImage startImage = null;
	public BufferedImage background = null;
	public BufferedImage overImage = null;

	public int  socre; 
    Bird bird ;
	Ground ground;
	Column column1;
	Column column2;
	
	public BirdGNew() throws IOException {
		// TODO Auto-generated constructor stub
		state = START;
		socre = 0;
		background = ImageIO.read(getClass().getResource("bg.png"));
		startImage = ImageIO.read(getClass().getResource("start.png"));
		overImage = ImageIO.read(getClass().getResource("gameover.png"));
	    bird = new Bird();  //小鸟
	    ground  = new Ground();
	    column1 = new Column(1);
	    column2 = new Column(2);
	}

	// 重写绘图方法

	@Override
	public void paint(Graphics g) {  
		// TODO Auto-generated method stub
		super.paint(g);
	    g.drawImage(background, 0, 0, null);
	    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
	    g.setFont(font);
	    g.drawString(socre+"", 40, 60);
	    g.setColor(Color.WHITE);
	    g.drawString(socre+"", 40-3, 60-3);
	    
	      // 转成2D画笔，旋转画布
	 		Graphics2D graphics2d = (Graphics2D) g;
	 		// 旋转画布 参数: 1.旋转弧度 2，3 旋转点
	 		graphics2d.rotate(-bird.rad, bird.x, bird.y);
	 		
	 		
	    g.drawImage(bird.image, bird.x - bird.width/2, bird.y - bird.height/2, null);
	 // 将画布转正
 		graphics2d.rotate(bird.rad, bird.x, bird.y);
	    g.drawImage(column1.image, column1.x-column1.width/2, column1.y-column1.height/2, null);
	    g.drawImage(column2.image, column2.x-column1.width/2, column2.y-column2.height/2, null);
	    g.drawImage(ground.image, ground.x, ground.y, null);
	    if(state == 0){
	    	g.drawImage(startImage, 0, 0, null);
	    }else if(state == 2){
	    	g.drawImage(overImage, 0, 0, null);
	    }
	}

	public void action(){
		
		MouseListener listener = new MouseAdapter() {
		    //鼠标点击事件
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	// TODO Auto-generated method stub
		    	switch (state) {
				case START:
					state = RUNNING ; //准备状态点击进入运行状态					
					break;

				case RUNNING:
					bird.flyUp();
	                     				
     					break;
				case GAMEOVER:
					try {
						state = START;
						socre = 0;
						bird = new Bird();
						column1 = new Column(1);
						column2 = new Column(2);
						ground = new Ground();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
		    } 
			
		};
		
		this.addMouseListener(listener);  // 添加鼠标点击事件
		
		while (true) {
 		  	//死循环
		    switch (state) {
			case START:
               	bird.fly();			
				break;

			case RUNNING:
				ground.step();
                bird.fly(); 
                column1.step();
                column2.step();
                bird.step();
                
                if(bird.x == column1.x || bird.x == column2.x){
                	socre++;
                }
                
                if(bird.hit(column1)||bird.hit(column2)||bird.hit(ground)){
                	state = GAMEOVER;
                }
                
                
                break;  
		    
			case GAMEOVER:
				
				break;
			}	
			
		   repaint();	
		   
		   try {
			Thread.sleep(1000/60);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  	}

	}
	
	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame(); // 创建画板
		frame.setSize(440, 640); // 设置画板大小
		BirdGNew gNew = new BirdGNew();
		frame.add(gNew); // 画布加入到画板
		frame.setLocationRelativeTo(null); // 设置显示屏幕中间
		frame.setVisible(true);
        gNew.action();  
	}
   
	//小鸟类
	public class Bird{
		
	   private	int x , y ;
	   private  BufferedImage image;	
	   private  int size ;
	   private  int width,height; //宽和高度
	   private  BufferedImage [] images ;
	   private  int index ; //下角标
	   
	   private double  s ; //位移量
	   private double  time; //事件
	   private double  g;    //重力加速度
	   private double  v0;   //初始速度
	   private double  speeh; //当前速度
	   
	   private double rad;
	   
	   public Bird() throws IOException {
		// TODO Auto-generated constructor stub
	     x = 140;
	     y = 245;  //啊定义xy 坐标
	     size = 40;   
	     index = 0;
	    
	     v0 = 20;
	     g = 4;
	     time = 0.25;
	     speeh = v0;
	     
	     try {
			image = ImageIO.read(getClass().getResource("0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    width = image.getWidth();
	    height = image.getHeight();
	    
	    images = new BufferedImage [8]; 
	    for(int i =0;i<8;i++){
	    	images[i] = ImageIO.read(getClass().getResource(i+".png"));
	    }
	  
	   }
	   
	   public void fly(){
		   index++;
		   image = images[(index/8)%8];
	   }
	   
	   public void step(){
		
		   double  v = speeh;
		   s = v*time - g*time*time;
		   y = (int) (y-s);
		   speeh = v - g*time;
	       rad = Math.atan(s/8);
	   }
     
	   public void flyUp(){
		   speeh = v0;
	   }
	   
      public boolean hit(Column column ){
    	  
    	  if(x>column.x-size/2-column.width/2 && x<column.x+column.width/2+size/2){
    		  
    		  if(y>column.y -column.gap/2+size/2 && y<column.y+column.gap/2-size/2){
    			  return false;
    		  }
    		  return true;
    	  }
    	  
    	  return false ;
      }
	
      public boolean hit(Ground ground){
    	  
    	  if(y>=ground.y-size/2){
    		  rad = -Math.PI/2;
    		  return true;
    		  
    	  }
    	  return false ;
      }
      
      
	}
	
	public class Ground{
		
		private int x , y ;
		private BufferedImage image;
		
		public Ground() {
			// TODO Auto-generated constructor stub
	        x =  0;
	        y =  500;
		    try {
				image = ImageIO.read(getClass().getResource("ground.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void step(){
			x--;
            if(x ==-79){
            	x = 0 ;
            }
		}
	}
	
   
	public class Column{
	
	  private int  x,y;
	  private int  width,height;
	  private BufferedImage image;
	  private int  distance; //间隙
	  private int  gap ;
	  Random random = new Random();
	  
	  public Column(int n ) throws IOException {
		// TODO Auto-generated constructor stub
	    image = ImageIO.read(getClass().getResource("column.png")); 
	    width = image.getWidth();
	    height = image.getHeight();	
	    gap = 144;
	    distance = 245;
	    
	    x = (n-1)*distance+550;
	    y = random.nextInt(218)+132;
	     
	  }
	  
	  public void step(){
		  x--;
		  if(x == -width/2){
			  x = distance*2 - width/2;
			  y = random.nextInt(218)+132;
		  }
	  }
	  
	}
	
}
