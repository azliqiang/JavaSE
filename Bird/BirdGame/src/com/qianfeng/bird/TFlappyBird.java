package com.qianfeng.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * FlapplyBird Game JDK 1.7 tool eclipse
 * 
 * @author zwf
 */
@SuppressWarnings("serial")
public class TFlappyBird extends JPanel {

	// ��Ϸ�÷�
	private int score = 0;
	// ��Ϸ״̬
	private int state = 0;

	// ��Ϸ���е�����״̬
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 2;

	// ���� С�� ���� ����
	private Bird bird = null;
	private TBird tbird = null;
	private Ground ground = null;
	private Column column1 = null;
	private Column column2 = null;

	// ��Ϸ����ͼƬ
	private BufferedImage background = null;
	// ��Ϸ��ʼ״̬ͼƬ
	private BufferedImage startImage = null;
	// ��Ϸ����ͼƬ
	private BufferedImage endImage = null;

	/* ���췽������ʼ����������ȡͼƬ��Դ */
	public TFlappyBird() throws Exception {
		// ��ʼֵΪ��ʼ��ʼ״̬
		state = START ;
		// ��ʼ����Ϊ0��
		score = 0;

		// ��ȡͼƬ ��Ϸ���� ��ʼͼƬ ����ͼƬ
		background = ImageIO.read(getClass().getResource("bg.png"));
		startImage = ImageIO.read(getClass().getResource("start.png"));
		endImage = ImageIO.read(getClass().getResource("gameover.png"));

		// ���� getClass().getResource(picName)���ͼƬ·����ΪUrl ָ�򱾵�ͼƬ����·��
		System.out.println(getClass().getResource("bg.png"));
		// file:/C:/Users/Administrator/Desktop/BirdGame/bin/com/qianfeng/bird/bg.png//

		// ʵ����С�����
		bird = new Bird();
		tbird = new TBird();
		// ʵ���������
		ground = new Ground();
		// ʵ���������ӵĶ���
		column1 = new Column(1);
		column2 = new Column(2);
	}

	/* JPanel��ͼ���� */
	public void paint(Graphics g) {
		// ���Ʊ��� ����Ļ��ԭ�㿪ʼ�� ����ͨ���޸� 2 3 ����ֵ �鿴��ʾЧ��
		g.drawImage(background, 0, 0, null);

		// ��������ͼƬ
		g.drawImage(column1.image, column1.x - column1.width / 2, column1.y
				- column1.height / 2, null);
		g.drawImage(column2.image, column2.x - column2.width / 2, column2.y
				- column2.height / 2, null);

		// ���� sans_serif ���� 40 ��
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		g.setFont(font);
		g.drawString(score + "", 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(score + "", 40 - 3, 60 - 3);

		// ���Ƶ��� -- ������Ҫ�����Ӻ���л��ƣ�
		g.drawImage(ground.image, ground.x, ground.y, null);

		// ת��2D���ʣ���ת����
		Graphics2D graphics2d = (Graphics2D) g;
		// ��ת���� ����: 1.��ת���� 2��3 ��ת��
		graphics2d.rotate(-bird.rad, bird.x, bird.y);
		g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height
				/ 2, null);
		// ������ת��
		graphics2d.rotate(bird.rad, bird.x, bird.y);

		Graphics2D grap = (Graphics2D) g;
		grap.rotate(-tbird.rad, tbird.x, tbird.y);
		g.drawImage(tbird.image, tbird.x - tbird.width / 2, tbird.y
				- tbird.height / 2, null);
		// ������ת��
		grap.rotate(tbird.rad, tbird.x, tbird.y);

		switch (state) {
		case START:
			g.drawImage(startImage, 0, 0, null);
			break;
		case GAMEOVER:
			g.drawImage(endImage, 0, 0, null);
			break;
		}

	}

	/* �������Լ�ҳ��ˢ�·��� */
	public void action() throws Exception {
		// �������¼�Adapter
		MouseListener listener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case RUNNING:
					// ���Ϸ�
					System.out.println("e.getModifiers() :" + e.getModifiers());
					if (e.getModifiers() == 16) {
						bird.flyUp();
					} else if (e.getModifiers() == 4) {
						tbird.flyUp();
					}
					break;
				case GAMEOVER:
					state = START;
					score = 0;
					try {
						bird = new Bird();
						// �����ڶ�ֻС��
						tbird = new TBird();
						column1 = new Column(1);
						column2 = new Column(2);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					break;

				}
			}

		};
		// ������¼��ҵ�Jpanel��
		addMouseListener(listener);
		// ѭ��ˢ��Jpanel
		while (true) {
			switch (state) {
			case RUNNING:
				bird.fly();
				tbird.fly();
				ground.step();
				column1.step();
				column2.step();
				bird.step();
				tbird.step();
				// �ж���Ϸ�Ƿ����
				if (bird.hit(column1) || bird.hit(column2) || bird.hit(ground)
						|| tbird.hit(column1) || tbird.hit(column2)
						|| tbird.hit(ground)) {
					state = GAMEOVER;
				}
				// �������
				if (bird.x == column1.x || bird.x == column2.x
						|| tbird.x == column1.x || tbird.x == column2.x) {
					score++;
				}
				break;

			case START:
				bird.fly();
				tbird.fly();
				ground.step();
				break;
			}
			// ˢ��Jpanel����
			repaint();
			Thread.sleep(1000 / 60);

		}

	}

	/**
	 * С����
	 * 
	 * @author Administrator
	 */
	public class Bird {
		private BufferedImage image = null;
		private int width = 0;
		private int height = 0;
		// С��ߴ磬ײ���ж�ʱ��ʹ��
		private int size = 0;
		// X Y �����
		private int x = 0;
		int y = 0;

		// С��ͼƬ��������
		BufferedImage[] images = null;
		// С������С�ű�
		int index = 0;

		double v0 = 0; // ��ʼ�ٶ�
		double g = 0; // �������ٶ�
		double time = 0; // ʱ��
		double s = 0; // �¼��ڵ�λ�ƾ���
		double rad = 0; // �Ƕ�
		double speeh = 0; // ��ǰ�ٶ�

		/* ���췽����ʼ������ */
		public Bird() throws Exception {

			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();
			size = 40;

			images = new BufferedImage[8];
			index = 0;

			x = 140;
			y = 235;

			// �����˶�״̬����
			v0 = 20;
			g = 4;
			time = 0.25;
			s = 0;
			speeh = v0;
			rad = 0;

			// ѭ�����������ͼƬ
			for (int i = 0; i < 8; i++) {
				images[i] = ImageIO.read(getClass().getResource(i + ".png"));
			}
		}

		/* �л�С����ʾͼƬ */
		public void fly() {
			index++;
			// ͼƬ�л�����12���ٶ�
			image = images[(index / 12) % 8];
		}

		/* С���ƶ��������޸�Bird y ����ֵ�� */
		public void step() {
			// �����ʼ�ٶ�
			double v = speeh;
			// ����ʱ���ڵ�λ�� v*t - g*t*t/2
			s = v * time - g * time * time / 2;
			// double ת�� int ���㾭��ʱ����λ��
			y = y - (int) s;
			// ���㾭���趨ʱ�����ٶ� --����ٶ�Ϊ���� �����ƶ� �ٶ�Ϊ���� �����ƶ�
			speeh = v - g * time;
			// ��С�����л���ֵ sΪY��λ�� X��λ��д��8
			rad = Math.atan(s / 8);
		};

		/* С�����Ϸɵķ��� */
		public void flyUp() {
			speeh = v0;
		}

		/* ײ����ⷽ�� */
		public boolean hit(Column column) {
			// ���С��X�� �����ӷ�Χ��
			if (x > column.x - column.width / 2 - size / 2
					&& x < column.x + column.width / 2 + size / 2) {
				// ��������ӵķ�϶�з���false
				if (y > column.y - column.gap / 2 + size / 2
						&& y < column.y + column.gap / 2 - size / 2) {
					return false;
				}
				return true;
			}
			return false;
		}

		/* ײ�������ⷽ�� */
		public boolean hit(Ground ground) {

			if (y >= 500 - size / 2) {
				// ײ���������ö�Ӧ��
				rad = -Math.PI / 2;
				return true;
			}
			return false;
		}

	}

	/**
	 * С����
	 * 
	 * @author Administrator
	 */
	public class TBird {
		private BufferedImage image = null;
		private int width = 0;
		private int height = 0;
		// С��ߴ磬ײ���ж�ʱ��ʹ��
		private int size = 0;
		// X Y �����
		private int x = 0;
		int y = 0;

		// С��ͼƬ��������
		BufferedImage[] images = null;
		// С������С�ű�
		int index = 0;

		double v0 = 0; // ��ʼ�ٶ�
		double g = 0; // �������ٶ�
		double time = 0; // ʱ��
		double s = 0; // �¼��ڵ�λ�ƾ���
		double rad = 0; // �Ƕ�
		double speeh = 0; // ��ǰ�ٶ�

		/* ���췽����ʼ������ */
		public TBird() throws Exception {

			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();
			size = 40;

			images = new BufferedImage[8];
			index = 0;

			x = 285;
			y = 235;

			// �����˶�״̬����
			v0 = 20;
			g = 4;
			time = 0.25;
			s = 0;
			speeh = v0;
			rad = 0;

			// ѭ�����������ͼƬ
			for (int i = 0; i < 8; i++) {
				images[i] = ImageIO.read(getClass().getResource(i + ".png"));
			}
		}
 
		/* �л�С����ʾͼƬ */
		public void fly() {
			index++;
			// ͼƬ�л�����12���ٶ�
			image = images[(index / 12) % 8];
		}

		/* С���ƶ��������޸�Bird y ����ֵ�� */
		public void step() {
			// �����ʼ�ٶ�
			double v = speeh;
			// ����ʱ���ڵ�λ�� v*t - g*t*t/2
			s = v * time - g * time * time / 2;
			// double ת�� int ���㾭��ʱ����λ��
			y = y - (int) s;
			// ���㾭���趨ʱ�����ٶ� --����ٶ�Ϊ���� �����ƶ� �ٶ�Ϊ���� �����ƶ�
			speeh = v - g * time;
			// ��С�����л���ֵ sΪY��λ�� X��λ��д��8
			rad = Math.atan(s / 8);
		};

		/* С�����Ϸɵķ��� */
		public void flyUp() {
			speeh = v0;
		}

		public boolean hit(Column column, int i) {
			if (x > column.x - column.width / 2 - size / 2
					&& x < column.x + column.width / 2 + size / 2) {
				if (y > column.y - column.gap / 2 + size / 2
						&& y < column.y + column.gap / 2 - size / 2) {

					return false;

				}

				return true;
			}
			return false;

		}

		/* ײ����ⷽ�� */
		public boolean hit(Column column) {
			// ���С��X�� �����ӷ�Χ��
			if (x > column.x - column.width / 2 - size / 2
					&& x < column.x + column.width / 2 + size / 2) {
				// ��������ӵķ�϶�з���false
				if (y > column.y - column.gap / 2 + size / 2
						&& y < column.y + column.gap / 2 - size / 2) {
					return false;
				}
				return true;
			}
			return false;
		}

		/* ײ�������ⷽ�� */
		public boolean hit(Ground ground) {

			if (y >= 500 - size / 2) {
				// ײ���������ö�Ӧ��
				rad = -Math.PI / 2;
				return true;
			}
			return false;
		}

	}

	/** ������ */
	public class Ground {
		BufferedImage image = null;
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;

		/* ���췽����ʼ������ */
		public Ground() throws Exception {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("ground.png"));
			width = image.getWidth();
			height = image.getHeight();
			x = 0;
			y = 500;
		}

		/* �����ƶ����� */
		public void step() {
			x--; // ���Ƶ����ƶ��ٶ�
			if (x == -109) {
				x = 0;
			}

		}

	}

	/** ������ */
	public class Column {
		BufferedImage image = null;
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		int gap = 0;
		int distance = 0;
		// �����
		Random random = new Random();

		/* ���췽����ʼ������ */
		public Column(int n) throws Exception {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("column.png"));
			width = image.getWidth();
			height = image.getHeight();
			// ��������֮��ľ���
			distance = 245;
			// ���ӵļ�϶
			gap = 144;
			// ����Y�������ֵ��Χ 218 - 350
			y = random.nextInt(218) + 132;
			// �������ӵľ���
			x = 550 + (n - 1) * distance;
		}

		/* �����ƶ� ���� */
		public void step() {
			// �����ƶ��ٶ�
			x--;
			// ����������Ļ��ʧ
			if (x == -width / 2) {
				// ����Column �µ�Xֵ
				x = 2 * distance - width / 2;
				// Y���ֵ
				y = random.nextInt(218) + 132;
			}
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 *             into java application
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("���С��");
		TFlappyBird birdGame = new TFlappyBird();
		frame.add(birdGame);
		// ���ô���Size
		frame.setSize(440, 670);
		// ����λ����Ļ����
		frame.setLocationRelativeTo(null);
		// �������رռ��˳�����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ��ʾ����
		frame.setVisible(true);
		// �����������¼���ˢ��ҳ���¼�
		birdGame.action();

	}

}