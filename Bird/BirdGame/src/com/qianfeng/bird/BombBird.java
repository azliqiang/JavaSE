package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * FlapplyBird Game JDK 1.7 tool eclipse
 * 
 * @author zwf
 */
@SuppressWarnings("serial")
public class BombBird extends JPanel {

	// 游戏得分
	private int score = 0;
	// 游戏状态
	private int state = 0;
   
	// 游戏运行的四种状态
	public static final int PREPARE =4;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 2;

	// 声明 小鸟 地面 柱子
	private Bird bird      = null;
	private Ground ground  = null;
	private Column column1 = null;
	private Column column2 = null;
    // 炸弹类
	private Bomb bomb      = null;
	// 游戏背景图片
	private BufferedImage background = null;
	// 游戏开始状态图片
	private BufferedImage startImage = null;
	// 游戏结束图片
	private BufferedImage endImage = null;

	/* 构造方法，初始化变量，读取图片资源 */
	public BombBird() throws Exception {
		// 初始值为开始开始状态
		state = PREPARE;
		// 初始分数为0分
		score = 0;

		// 读取图片 游戏背景 开始图片 结束图片
		background = ImageIO.read(getClass().getResource("bg.png"));
		startImage = ImageIO.read(getClass().getResource("start.png"));
		endImage = ImageIO.read(getClass().getResource("gameover.png"));

		// 分析 getClass().getResource(picName)获得图片路径，为Url 指向本地图片保存路径
		System.out.println(getClass().getResource("bg.png"));
		// file:/C:/Users/Administrator/Desktop/BirdGame/bin/com/qianfeng/bird/bg.png//

		// 实例画小鸟对象
		bird = new Bird();
		// 实例地面对象
		ground = new Ground();
		// 实例两根柱子的对象
		column1 = new Column(1);
		column2 = new Column(2);
        //实例化炸弹类对象
		bomb = new Bomb();
        File file = new File("yeahboom.mp3");
        
	    AudioFileFormat stream =  AudioSystem.getAudioFileFormat(file);
	    AudioFormat f  =  stream.getFormat();
	    		
		
	}

	/* JPanel绘图方法 */
	public void paint(Graphics g) {
		// 绘制背景 从屏幕的原点开始画 可以通过修改 2 3 参数值 查看显示效果
		g.drawImage(background, 0, 0, null);

		// 绘制柱子图片
		g.drawImage(column1.image, column1.x - column1.width / 2, column1.y
				- column1.height / 2, null);
		g.drawImage(column2.image, column2.x - column2.width / 2, column2.y
				- column2.height / 2, null);

		// 字体 sans_serif 粗体 40 号
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		g.setFont(font);
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score + "", 40 - 3, 60 - 3);

		// 绘制地面 -- 地面需要在柱子后进行绘制！
		g.drawImage(ground.image, ground.x, ground.y, null);

		// 转成2D画笔，旋转画布
		Graphics2D graphics2d = (Graphics2D) g;
		// 旋转画布 参数: 1.旋转弧度 2，3 旋转点
		graphics2d.rotate(-bird.rad, bird.x, bird.y);
		g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height
				/ 2, null);
		// 将画布转正
		graphics2d.rotate(bird.rad, bird.x, bird.y);
        //画炸弹
		g.drawImage(bomb.image,bomb.x-bomb.width/2,bomb.y-bomb.height/2,null);
   		
		switch (state) {
		case START:
		case PREPARE:
			g.drawImage(startImage, 0, 0, null);
			break;
		case GAMEOVER:
			g.drawImage(endImage, 0, 0, null);
			break;
		}

	}

	/* 鼠标监听以及页面刷新方法 */
	public void action() throws Exception {
		// 鼠标监听事件Adapter
		MouseListener listener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				switch (state) {
				case PREPARE:
					state = START;
					break;
				case START:
					state = RUNNING;
					break;
				case RUNNING:
					// 向上飞
					bird.flyUp();
					break;
				case GAMEOVER:
					state = PREPARE;
					score = 0;
					try {
						bird = new Bird();
						column1 = new Column(1);
						column2 = new Column(2);
						bomb= new Bomb();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					break;

				}
			}

		};
		// 鼠标点击事件挂到Jpanel上
		addMouseListener(listener);
		// 循环刷新Jpanel
		while (true) {
			switch (state) {
			case RUNNING:
				bird.fly();
				ground.step();
				column1.step();
				column2.step();
				bird.step();
				bomb.step();
				bomb.fly();
				// 判断游戏是否结束
				if (bird.hit(column1) || bird.hit(column2) || bird.hit(ground)||bird.hit(bomb)) {
					state = GAMEOVER;
				}
				// 计算分数
				if (bird.x == column1.x || bird.x == column2.x) {
					score++;
				}
				break;
			case PREPARE:
				bird.fly();
				break;
			case START:
				bird.fly();
				ground.step();
				break;
			}
			// 刷新Jpanel方法
			repaint();
			Thread.sleep(1000 / 60);

		}

	}

	/**
	 * 小鸟类
	 * 
	 * @author Administrator
	 */
	public class Bird {
		private BufferedImage image = null;
		int width = 0;
		int height = 0;
		// 小鸟尺寸，撞击判断时候使用
		int size = 0;
		// X Y 坐标点
		int x = 0;
		int y = 0;

		// 小鸟图片保存数组
		BufferedImage[] images = null;
		// 小鸟数组小脚标
		int index = 0;

		double v0 = 0; // 初始速度
		double g = 0; // 重力加速度
		double time = 0; // 时间
		double s = 0; // 事件内的位移距离
		double rad = 0; // 角度
		double speeh = 0; // 当前速度

		/* 构造方法初始化数据 */
		public Bird() throws Exception {

			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();
			size = 40;

			images = new BufferedImage[8];
			index = 0;

			x = 140;
			y = 235;

			// 物理运动状态参数
			v0 = 20;
			g = 4;
			time = 0.25;
			s = 0;
			speeh = v0;
			rad = 0;

			// 循环往数组添加图片
			for (int i = 0; i < 8; i++) {
				images[i] = ImageIO.read(getClass().getResource(i + ".png"));
			}
		}

		/* 切换小鸟显示图片 */
		public void fly() {
			index++;
			// 图片切换放慢12倍速度
			image = images[(index / 12) % 8];
		}

		/* 小鸟移动方法（修改Bird y 坐标值） */
		public void step() {
			// 赋予初始速度
			double v = speeh;
			// 计算时间内的位移 v*t - g*t*t/2
			s = v * time - g * time * time / 2;
			// double 转成 int 计算经过时间后的位移
			y = y - (int) s;
			// 计算经过设定时候后的速度 --如果速度为正数 向上移动 速度为负数 向下移动
			speeh = v - g * time;
			// 求小鸟反正切弧度值 s为Y轴位移 X轴位移写成8
			rad = Math.atan(s / 8);
		};

		/* 小鸟向上飞的方法 */
		public void flyUp() {
			speeh = v0;
		}

		/* 撞击检测方法 */
		public boolean hit(Column column) {
			// 如果小鸟X轴 在柱子范围内
			if (x > column.x - column.width / 2 - size / 2
					&& x < column.x + column.width / 2 + size / 2) {
				// 如果在柱子的缝隙中返回false
				if (y > column.y - column.gap / 2 + size / 2
						&& y < column.y + column.gap / 2 - size / 2) {
					return false;
				}
				return true;
			}
			return false;
		}
        /*撞击地面检测方法*/
		public boolean hit(Ground ground) {
            
			if (y >= 500 - size / 2) {
                //撞击地面设置对应弧
				rad = -Math.PI / 2;
				return true;
			}
			return false;
		}
       
		// 检测当前的鸟是否撞到导弹
		public boolean hit(Bomb bomb) {
	     
		if (x > bomb.x - bomb.size / 2 - width/2
							&& x < bomb.x + bomb.width/ 2 + width/ 2) {
						// 检测是否在缝隙中
						if (y > bomb.y -bomb.height/2 - height/2
								&& y < bomb.y + bomb.height / 2 + height/2) {
							return true;
						}
						return false;
					}
					return false;
				}	
	}

  /**地面类*/	
  public class Ground {
		BufferedImage image = null;
		int  width  = 0 ; 
		int  height = 0;
		int x = 0;
		int y = 0;
        
		/*构造方法初始化数据*/
		public Ground() throws Exception {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("ground.png"));
			width = image.getWidth();
			height = image.getHeight();
			x = 0;
			y = 500;
		}
        
		/*地面移动方法*/
		public void step() {
			x--;    //控制地面移动速度
			if (x == -109) {
				x = 0;
			}

		}

	}

  /**柱子类*/
  public class Column {
		BufferedImage image = null;
		int width = 0 ; 
		int height = 0;
		int x = 0; 
		int y = 0;
		int gap = 0;
		int distance = 0;
		//随机数
		Random random = new Random();
		
		/*构造方法初始化数据*/
		public Column(int n) throws Exception {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("column.png"));
			width = image.getWidth();
			height = image.getHeight();
			//两根柱子之间的距离
			distance = 245;    
			//柱子的间隙
			gap = 144;
            //柱子Y坐标随机值范围  218 - 350
			y = random.nextInt(218) + 132;
	        //两根柱子的距离
			x = 550 + (n - 1) * distance;
		}
        /*柱子移动 方法*/
		public void step() {
			//控制移动速度
			x--;   
			//当柱子在屏幕消失
			if (x == -width / 2) {
				//赋予Column 新的X值
				x = 2 * distance - width / 2;
	     		//Y随机值
				y = random.nextInt(218) + 132;
			}
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 *  into java application
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("疯狂小鸟");
		BombBird birdGame = new BombBird();
		frame.add(birdGame);
		// 设置窗体Size
		frame.setSize(440, 670);
		// 窗体位于屏幕中央
		frame.setLocationRelativeTo(null);
		// 点击窗体关闭键退出窗体
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 显示窗体
		frame.setVisible(true);
		// 启动鼠标监听事件和刷新页面事件
		birdGame.action();

	}
 
	/**炸弹类*/
	public  class Bomb{			 
		 
		private  BufferedImage image  = null;
		private  int x =0 ;
		private  int y =0;
		private  int width = 0 ;
		private  int height = 0;
		private  int size =0;
		Random randomX = new Random();
		Random randomY = new Random();
		   
		BufferedImage [] images ;
		   
		private int index =0 ;
		   
		   public Bomb() throws Exception {
			   
			   image  = ImageIO.read(getClass().getResource("rocket1.png"));
			   //初始化炸弹X坐标
			   x = 500; 
			   //初始化Y坐标
			   y = randomY.nextInt(450);
			   size = 45;
			   width = image.getWidth();
			   height = image.getHeight();
			   index =0;
			   
			   images = new BufferedImage [2];
			   
			   images[0] = ImageIO.read(getClass().getResource("rocket1.png"));
			   images[1] = ImageIO.read(getClass().getResource("rocket2.png"));
			   
		}
		   
		   public void fly(){
			   index++;
			   image = images[(index/12)%2];
		   }
		   
		  public void step(){
			  x-=3;
			  if(x==-100){
				  x= 500;
				  y=randomY.nextInt(450);
			  }
			  
		  }
		   
	 }

	
}