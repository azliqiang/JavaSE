package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * birdgame 游戏类 2015-8-18
 * */
public class BG extends JPanel {
	// 继承

	public int state; // 游戏状态
	public static final int START = 0; // 开始
	public static final int RUNNING = 1; // 运行状态
	public static final int GAMEOVER = 2; // 游戏结束状态
	public BufferedImage background; // 背景图片
	public BufferedImage startimage; // 开始这张图片
	public BufferedImage overimage; // 游戏结束图片
	public int score; // 得分
	Bird bird; // 创建一个鸟类
	Ground ground; // 创建地面类
	Column column1;
	Column column2;

	public BG() throws IOException {
		// TODO Auto-generated constructor stub
		state = START; // 游戏开始时是开始状态
		background = ImageIO.read(getClass().getResource("bg.png"));
		startimage = ImageIO.read(getClass().getResource("start.png"));
		overimage = ImageIO.read(getClass().getResource("gameover.png"));
		score = 0;
		bird = new Bird(); // 真正创建鸟类对象
		ground = new Ground();// 真正创建地面类对象
		column1 = new Column(1);
		column2 = new Column(2);

	}

	// alt+ /
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(background, 0, 0, null); // 画背景
		// 在开始的状态下才可以画这张图片
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);// 创建一个字体
		g.setFont(font); // 把字体设置给了画笔
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE); // 把画笔改为白色
		g.drawString(score + "", 40 - 3, 60 - 3); // 画一个白色的字母
		
		Graphics2D graphics2d = (Graphics2D)g;// 转换一个2d的笔
		graphics2d.rotate(-bird.rad,bird.x-bird.width/2,bird.y - bird.height/2);
		g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height
				/ 2 ,null);
		graphics2d.rotate(bird.rad,bird.x-bird.width/2,bird.y - bird.height/2);
		// 画一个小鸟
		g.drawImage(column1.image, column1.x - column1.width / 2, column1.y
				- column1.height / 2, null);
		g.drawImage(column2.image, column2.x - column2.width / 2, column2.y
				- column2.height / 2, null);
		g.drawImage(ground.image, ground.x, ground.y, null);// 画一个地面

		if (state == START) {
			g.drawImage(startimage, 0, 0, null); // 画开始
		}
		if (state == GAMEOVER)
			g.drawImage(overimage, 0, 0, null);

	}

	// 循环刷新的方法
	public void action() {
		// 创建鼠标点击事件
		MouseListener listener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				switch (state) {
				case START:
					state = RUNNING;
					break;

				case RUNNING:
					bird.flyup();
					break;

				case GAMEOVER:
					state = START;
					score = 0;
					try {
						bird = new Bird();
						ground = new Ground();
						column1 = new Column(1);
						column2 = new Column(2);
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
		
					
					
				
					break;
             }
			}
			
//		@Override
//		public void mouseMoved(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//			  int x=e.getX();
//	           int y=e.getY();
//	           String s="当前鼠标坐标:"+x+','+y;
//	           System.out.println(s);
//		}	
			
		};
		
		
//		this.requestFocus();
//		KeyListener keyListener  = new KeyListener() {
//			
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("``````");
//			}
//			
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("``````");
//			}
//			
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("``````");
//			}
//		};
//		
//		this.addKeyListener(keyListener);
		
		MouseMotionListener listener2 = new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				int x=e.getX();
		           int y=e.getY();
		           String s="当前鼠标坐标:"+x+','+y;
		           System.out.println(s);
			}	
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
	
		this.addMouseListener(listener);
        this.addMouseMotionListener(listener2);
		while (true) {
			// 循环体
			switch (state) {
			case START:
				bird.fly();
				ground.step();
				break;
			case RUNNING:
				ground.step();
				bird.fly();
				bird.step();
				column1.step();
				column2.step();
				// 游戏撞击方法
				if (bird.hit(column1) || bird.hit(column2) || bird.hit(ground)) {
					state = GAMEOVER;
				}

				break;
			case GAMEOVER:

				break;
			}

			repaint(); // 重新绘制
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame(); // 创建画板对象
		boolean b = frame.getFocusableWindowState();
	     System.out.println("b"+b);
		BG birdgame = new BG(); // 创建画布
		frame.add(birdgame); // 把画布加到画板上去
		frame.setSize(440, 670); // 设置画板大小
		frame.setLocationRelativeTo(null); // 画板位于屏幕的中间
		frame.setVisible(true); // 让画板显示
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("!!");
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("!!");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("!!");
			}
		});
		birdgame.action();
		// ctrl+f11 运行
		
	     
	}

	// 创建一个鸟类
	public class Bird {

		public int x; // x坐标
		public int y; // y坐标
		public BufferedImage image; // 图片
		public int width; // 宽度
		public int height; // 高度
		public int index; // 下角标
		public BufferedImage[] images; // 数组

		// 定义小鸟位移的属性
		public double time;
		public double speed; // 当前速度
		public double g; // 重力加速度
		public double s; // 位移
		public double v; // 定速
        public int size;  //定义鸟的尺寸
		public double rad; //弧度
		// 鸟类的构造方法
		public Bird() throws IOException {
			// TODO Auto-generated constructor stub
            size=40;
			time = 0.25;
			v = 20;
			speed = v;
			g = 4; // 赋值
			rad = 0.2; //弧度赋初始值

			x = 140;
			y = 240;
			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();

			index = 0;
			images = new BufferedImage[8];
			for (int i = 0; i < 8; i++)
				images[i] = ImageIO.read(getClass().getResource(i + ".png"));
			// ctrl+s ctrl+shift+s -format
		}

		// 小鸟飞的方法
		public void fly() {
			index++; // 下角标加1
			image = images[(index / 10) % 8]; // 取图片
		}

		// 向下落的方法
		public void step() {
			double p = speed; // 获取当前速度
			s = p * time - g * time * time / 2; // 位移
			y = (int) (y - s); // 位移后的s值
			speed = p - g * time; // 经过time变化后的速度
			 rad = Math.atan(s/8);  //拿到弧度
		}

		// 向上飞的方法
		public void flyup() {
			speed = v;
		}

		// 撞击地面的方法
		public boolean hit(Ground ground) {

			if (y >= ground.y) {
				return true;
			}
			return false;

		}

		// 撞击柱子
		public boolean hit(Column column) {
			// 满足条件
			if (x > column.x - column.width / 2 - size / 2
					&& x < column.x + column.width / 2 + size / 2) {
				// 判断小鸟是否在间隙中通过
				if (y > column.y - column.gap / 2 + size / 2
						&& y < column.y + column.gap / 2 - size / 2) {
					return false;
				}
				return true;
			}
			return false;
		}

	}

	// 地面类
	public class Ground {
		public int x;
		public int y;
		public int width;
		public int height;
		public BufferedImage image;

		public Ground() throws IOException {
			// TODO Auto-generated constructor stub
			x = 0;
			y = 500;
			image = ImageIO.read(getClass().getResource("ground.png"));
			width = image.getWidth();
			height = image.getHeight();
		}

		public void step() {
			x--;
			if (x == -100)
				x = 0;
		}

	}

	// 柱子类
	public class Column {
		public int x, y;
		public BufferedImage image;
		public int gap;// 间隙
		public int distance;// 间距
		public int width;
		public int height;
		Random random = new Random();

		public Column(int n) throws IOException {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("column.png"));
			width = image.getWidth();
			height = image.getHeight();
			gap = 144;
			distance = 245;

			x = (n - 1) * distance + 550;
			y = random.nextInt(218) + 132;

		}

		public void step() {
			x--;
			if (x == -width / 2) {
				x = 2 * distance - width / 2;
				y = random.nextInt(218) + 132;// 重置x坐标的时候 再重置下y坐标
			}
		}

	}

}
