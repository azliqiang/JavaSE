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
 * birdgame ��Ϸ�� 2015-8-18
 * */
public class BG extends JPanel {
	// �̳�

	public int state; // ��Ϸ״̬
	public static final int START = 0; // ��ʼ
	public static final int RUNNING = 1; // ����״̬
	public static final int GAMEOVER = 2; // ��Ϸ����״̬
	public BufferedImage background; // ����ͼƬ
	public BufferedImage startimage; // ��ʼ����ͼƬ
	public BufferedImage overimage; // ��Ϸ����ͼƬ
	public int score; // �÷�
	Bird bird; // ����һ������
	Ground ground; // ����������
	Column column1;
	Column column2;

	public BG() throws IOException {
		// TODO Auto-generated constructor stub
		state = START; // ��Ϸ��ʼʱ�ǿ�ʼ״̬
		background = ImageIO.read(getClass().getResource("bg.png"));
		startimage = ImageIO.read(getClass().getResource("start.png"));
		overimage = ImageIO.read(getClass().getResource("gameover.png"));
		score = 0;
		bird = new Bird(); // ���������������
		ground = new Ground();// �����������������
		column1 = new Column(1);
		column2 = new Column(2);

	}

	// alt+ /
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(background, 0, 0, null); // ������
		// �ڿ�ʼ��״̬�²ſ��Ի�����ͼƬ
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);// ����һ������
		g.setFont(font); // ���������ø��˻���
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE); // �ѻ��ʸ�Ϊ��ɫ
		g.drawString(score + "", 40 - 3, 60 - 3); // ��һ����ɫ����ĸ
		
		Graphics2D graphics2d = (Graphics2D)g;// ת��һ��2d�ı�
		graphics2d.rotate(-bird.rad,bird.x-bird.width/2,bird.y - bird.height/2);
		g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height
				/ 2 ,null);
		graphics2d.rotate(bird.rad,bird.x-bird.width/2,bird.y - bird.height/2);
		// ��һ��С��
		g.drawImage(column1.image, column1.x - column1.width / 2, column1.y
				- column1.height / 2, null);
		g.drawImage(column2.image, column2.x - column2.width / 2, column2.y
				- column2.height / 2, null);
		g.drawImage(ground.image, ground.x, ground.y, null);// ��һ������

		if (state == START) {
			g.drawImage(startimage, 0, 0, null); // ����ʼ
		}
		if (state == GAMEOVER)
			g.drawImage(overimage, 0, 0, null);

	}

	// ѭ��ˢ�µķ���
	public void action() {
		// ����������¼�
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
//	           String s="��ǰ�������:"+x+','+y;
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
		           String s="��ǰ�������:"+x+','+y;
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
			// ѭ����
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
				// ��Ϸײ������
				if (bird.hit(column1) || bird.hit(column2) || bird.hit(ground)) {
					state = GAMEOVER;
				}

				break;
			case GAMEOVER:

				break;
			}

			repaint(); // ���»���
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
		JFrame frame = new JFrame(); // �����������
		boolean b = frame.getFocusableWindowState();
	     System.out.println("b"+b);
		BG birdgame = new BG(); // ��������
		frame.add(birdgame); // �ѻ����ӵ�������ȥ
		frame.setSize(440, 670); // ���û����С
		frame.setLocationRelativeTo(null); // ����λ����Ļ���м�
		frame.setVisible(true); // �û�����ʾ
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
		// ctrl+f11 ����
		
	     
	}

	// ����һ������
	public class Bird {

		public int x; // x����
		public int y; // y����
		public BufferedImage image; // ͼƬ
		public int width; // ���
		public int height; // �߶�
		public int index; // �½Ǳ�
		public BufferedImage[] images; // ����

		// ����С��λ�Ƶ�����
		public double time;
		public double speed; // ��ǰ�ٶ�
		public double g; // �������ٶ�
		public double s; // λ��
		public double v; // ����
        public int size;  //������ĳߴ�
		public double rad; //����
		// ����Ĺ��췽��
		public Bird() throws IOException {
			// TODO Auto-generated constructor stub
            size=40;
			time = 0.25;
			v = 20;
			speed = v;
			g = 4; // ��ֵ
			rad = 0.2; //���ȸ���ʼֵ

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

		// С��ɵķ���
		public void fly() {
			index++; // �½Ǳ��1
			image = images[(index / 10) % 8]; // ȡͼƬ
		}

		// ������ķ���
		public void step() {
			double p = speed; // ��ȡ��ǰ�ٶ�
			s = p * time - g * time * time / 2; // λ��
			y = (int) (y - s); // λ�ƺ��sֵ
			speed = p - g * time; // ����time�仯����ٶ�
			 rad = Math.atan(s/8);  //�õ�����
		}

		// ���Ϸɵķ���
		public void flyup() {
			speed = v;
		}

		// ײ������ķ���
		public boolean hit(Ground ground) {

			if (y >= ground.y) {
				return true;
			}
			return false;

		}

		// ײ������
		public boolean hit(Column column) {
			// ��������
			if (x > column.x - column.width / 2 - size / 2
					&& x < column.x + column.width / 2 + size / 2) {
				// �ж�С���Ƿ��ڼ�϶��ͨ��
				if (y > column.y - column.gap / 2 + size / 2
						&& y < column.y + column.gap / 2 - size / 2) {
					return false;
				}
				return true;
			}
			return false;
		}

	}

	// ������
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

	// ������
	public class Column {
		public int x, y;
		public BufferedImage image;
		public int gap;// ��϶
		public int distance;// ���
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
				y = random.nextInt(218) + 132;// ����x�����ʱ�� ��������y����
			}
		}

	}

}
