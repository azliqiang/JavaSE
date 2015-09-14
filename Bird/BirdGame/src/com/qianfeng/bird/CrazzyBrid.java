package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CrazzyBrid extends JPanel {

	BufferedImage background;

	int state; // 游戏状态
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 2;
    
	int score; // 分数
    
	BufferedImage gameOverImage;
	BufferedImage startImage;

	private Brid brid;
    private Ground ground;
	private Clounm clounm1;
	private Clounm clounm2;
    
	public CrazzyBrid() throws Exception {
		// TODO Auto-generated constructor stub
		state = START;
		startImage = ImageIO.read(getClass().getResource("start.png"));
		gameOverImage = ImageIO.read(getClass().getResource("gameover.png"));
		background = ImageIO.read(getClass().getResource("bg.png"));
		score = 0;
		brid = new Brid();
		ground = new Ground();
		clounm1 = new Clounm(1);
		clounm2 = new Clounm(2);

	}

	public void action() throws Exception {

		MouseListener listener = new MouseAdapter() {
           //Mouse按下监听事件
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				switch (state) {
				case GAMEOVER:
					try {
						clounm1 = new Clounm(1);
						clounm1 = new Clounm(1);
						brid = new Brid();
						score = 0;
						state = START;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case START:
					state = RUNNING;
					break;
				case RUNNING:
//					 调用bird fly()飞方法
					brid.flyup();
					break;
				}
			}
		};

		addMouseListener(listener);
 
		while (true) {

			switch (state) {
			case START:
                brid.fly();
                ground.step();
				break;
			case RUNNING:
				ground.step();
                clounm1.step();
                clounm2.step();
                brid.fly();
                brid.step();
                //计分器
                if(brid.x == clounm1.x || brid.x == clounm2.x){
                	score ++;
                }
                
                if(brid.hit(clounm1) || brid.hit(clounm2) ||brid.hit(ground)){
                	state = GAMEOVER;
                }
                
				break;
			}
		
          repaint();
          Thread.sleep(1000/60);
		}

	}

	// 小鸟类
	class Brid {

		BufferedImage image;
		int x, y;
		int width, height;
		int size;
		double g;
		double t;// 两次位置的时间
		double v0;// 初始向上抛速度
		double speed;// 当前的上抛速度
		double s; // 是经过时间t以后的位移
		double alpha;// 鸟的倾斜弧度

		BufferedImage images[]; // 放图片的数组
		int index; // 图片下脚标
      //小鸟的构造方法 初始化数据
		public Brid() throws Exception {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();

			x = 132;
			y = 240;
          
			
			size  = 40; // 小鸟尺寸大小
			g = 4;      // 加速度4
			v0 = 20;    // 向上初速度
			t = 0.25;   // 两次的间隔
			speed =v0;  // 把当前速度付值为初始速度
			s=0;        // 位移为0
			alpha = 0;  // 小鸟飞行角度
		
			images = new BufferedImage [8] ;
			
			for(int i=0;i<8;i++){
				images[i] = ImageIO.read(getClass().getResource(i+".png"));
				
			}
			
			index = 0;
		}
     //小鸟翅膀动的方法
	  public void fly(){
		  index++;
		  image = images[(index / 12) % 8];
	  }	
	//上次下移动
	  public void step(){
	   double vo = speed;
	   s = vo*t -g*t*t/2; //位移计算
	   y = y-(int)s;
	   double v = vo-g*t; //计算移动后的速度
	   speed = v;         //移动后的速度赋给初始速度
	   
	   alpha = Math.atan(s/8);
	   
	  }
	   //向上飞方法
	   public void   flyup(){
		  speed = v0; 
		   
	  }
	   
	  public boolean hit(Ground ground){
	  //是否撞击地面  
	  boolean ishit = y+size/2 > ground.y;
	  
	  if(ishit){
		  //如果撞击地面1.设置小鸟的y坐标
		  y=ground.y-size/2;
		  //设置小鸟的角度
		  alpha = -3.14159265358979323 / 2;
		  return true;
	   }
	    
	    return false;
	  } 
	   
	  //撞击柱子判断方法
	   public boolean hit(Clounm clounm){
		   
		   if(x>clounm.x-clounm.width/2-size/2 && x<clounm.x+clounm.width/2+size/2){
			   
			   if(y>clounm.y-clounm.gap/2+size/2 && y<clounm.y+clounm.gap/2-size/2){
				   
				   return false;
			   }
			   return true ;
		   }
		   
		   return false;
	   }
	  
	  
	   
	}
	
	
	
  //重写画图方法  先后顺序会影响显示效果
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(background, 0, 0, null);
		g.drawImage(clounm1.image,clounm1.x-clounm1.width/2,clounm1.y-clounm1.height/2,null);
		g.drawImage(clounm2.image,clounm2.x-clounm2.width/2,clounm2.y-clounm2.height/2,null);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		g.setFont(font);
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score + "", 40-3, 60-3); //黑色阴影
		g.drawImage(ground.image,ground.x,ground.y,null);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.rotate(-brid.alpha,brid.x,brid.y);
		g.drawImage(brid.image, brid.x-brid.width/2, brid.y-brid.height/2, null);
		graphics2d.rotate(brid.alpha,brid.x,brid.y);
		
		//如果要是开始或者游戏结束状态 更换背景
		switch (state) {
		case GAMEOVER:
			g.drawImage(gameOverImage,0,0,null);
			break;
		case START:
			g.drawImage(startImage, 0, 0, null);
			break;
		
		}
	}

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame();
		CrazzyBrid brid = new CrazzyBrid();
		frame.add(brid);
		frame.setSize(440, 670);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		brid.action();
	}

	class Ground {
		
		BufferedImage image;
		int x,y;
		int width,height;
		
		//初始化数据
		public Ground() throws IOException {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("ground.png"));
			x= 0;
			y=500;
			width = image.getWidth();
			height = image.getHeight();
		}
		
		public void step(){
			x--;
			if(x==-109){
				x=0;
			}
			System.out.println(x);
		}
	}
	
	/**
	 * 柱子类型 x,y是柱子的中心位置
	 * */
	class Clounm{
	
	BufferedImage image ;	
	int x,y;
	int width,height;
	int gap ;  //上下间隙
	int distance ; //两根柱子的距离
	Random random = new Random();
	
	public Clounm(int n ) throws IOException {
		// TODO Auto-generated constructor stub
		image = ImageIO.read(getClass().getResource("column.png"));
		width = image.getWidth();
		height = image.getHeight();
		gap = 144;
		distance = 245;
		x = 550+(n-1)*distance;
		y = random.nextInt(218)  +132;
		
	}
	//Column位置 改变访法	
	public void step(){
		
		x--;
		// x在画的时候要减width/2 当消失的时候在把他的X付值
		if(x == -width/2){
			x = distance*2 -width/2; //两个之间间隔是 2个245
			y = random.nextInt(218)+132;
		}
		
		
	}
	
	}
}
