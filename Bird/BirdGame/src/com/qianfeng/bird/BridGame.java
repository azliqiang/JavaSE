package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
//画布

public class BridGame extends JPanel {
	// 游戏状态
	private int state;
	// 开始值
	public static final int START = 0;
	// 运行值
	public static final int RUNNING = 1;
	// 游戏结束值
	public static final int GAMEOVER = 2;
	public BufferedImage bg;// 整体的背景
	public BufferedImage start;
	public BufferedImage gameover1;
	public int score;
	Brid brid;
	private Ground ground;

	/**
	 * @param args
	 * @throws IOException
	 */
	// 构造方法，初始化数据
	public BridGame() throws Exception {
		state = START;
		score = 0;
		bg = ImageIO.read(getClass().getResource("bg.png"));
		start = ImageIO.read(getClass().getResource("start.png"));
		gameover1 = ImageIO.read(getClass().getResource("gameover.png"));
		// 创建小鸟类实体
		brid = new Brid();
		// 创建地面
		ground = new Ground();
	}

	// 刷新页面方法

	@Override
	public void paint(Graphics g) {
		// 绘制图片
		g.drawImage(bg, 0, 0, null);
		g.drawImage(brid.image, brid.x - brid.width / 2, brid.y - brid.height
				/ 2, null);
		g.drawImage(ground.image, ground.x, ground.y, null);
		// 创建字体类型 字体类型 字体风格 大小

		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		// 给G 设置字体
		g.setFont(font);
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score + "", 40 - 3, 60 - 3);
		if (state == START) {
			// 画开始那张图片
			g.drawImage(start, 0, 0, null);
		} else if (state == GAMEOVER) {
			g.drawImage(gameover1, 0, 0, null);
		}
	}

	// 刷新页面方法
	public void action(){
			//鼠标监听事件
			MouseListener listener = new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
				    switch (state) {
					case START:
						state = RUNNING;
						break;

					case RUNNING:
						brid.flyUp();
						break;
					
					case GAMEOVER:
						break;
					}
					
				}
			};
			this.addMouseListener(listener);
			while(true){
				
				//分支语句
				switch (state) {
				case START:
					brid.fly();
					ground.step();
					break;
				case RUNNING:
					brid.fly();
					brid.step();
					ground.step();
					break;
				case GAMEOVER:
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
		//程序入口
		public  static void main(String[] args) throws Exception { 
			//创建窗体
JFrame  frame =new JFrame("我的小鸟！");
//创建一个画布对象
BridGame  bridGame =new BridGame();
frame.add(bridGame);
frame.setSize(440,650);
//窗体位于屏幕中心
frame .setLocationRelativeTo(null);
//设置窗体显示
frame.setVisible(true);
bridGame.action();//刷新方法
}
//这就是个小鸟类
    class Brid{
	//小鸟的背景图片
	private BufferedImage[] image1;
	private int x;
	private int y;
	private int width;//小鸟的宽度
	private int height;//小鸟的高度
	private int index;
	double g = 4;
	double v0 =20;
	double speech=0;
	double time =0.25;
	double s =0;
	BufferedImage image =null;
	//构造方法  初始化数据
	public Brid() {
		try {
			image = ImageIO.read(getClass().getResource("0.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		width =image.getWidth();
		height =image.getHeight();
		speech =v0;
		x = 130;
		y =240;
		index =0;
		image1 =new BufferedImage[8];
		for(int i =0;i<8;i++){
		try {
			image1[i] =ImageIO.read(getClass().getResource(i+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	//小鸟飞的方法
	public void fly(){
		index++;
		image =image1[(index/10)%8];
	}
 public void step(){
	double p =speech;
	s =p*time-g*time*time/2;
	y =(int) (y-s);
	speech =p-g*time;
}
public void flyUp(){
	speech =v0;
}
	
}

	// 地面类
	class Ground {
		// 地面图片
		private BufferedImage image;
		private int x;// x坐标
		private int y;// y坐标

		public Ground() {
			try {
				image = ImageIO.read(getClass().getResource("ground.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x = 0;
			y = 500;
		}

		public void step() {
			x--;
			if (x == -109) {
				x = 0;
			}
		}
	}
}
