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

	int state; // ��Ϸ״̬
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 2;
    
	int score; // ����
    
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
           //Mouse���¼����¼�
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
//					 ����bird fly()�ɷ���
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
                //�Ʒ���
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

	// С����
	class Brid {

		BufferedImage image;
		int x, y;
		int width, height;
		int size;
		double g;
		double t;// ����λ�õ�ʱ��
		double v0;// ��ʼ�������ٶ�
		double speed;// ��ǰ�������ٶ�
		double s; // �Ǿ���ʱ��t�Ժ��λ��
		double alpha;// �����б����

		BufferedImage images[]; // ��ͼƬ������
		int index; // ͼƬ�½ű�
      //С��Ĺ��췽�� ��ʼ������
		public Brid() throws Exception {
			// TODO Auto-generated constructor stub
			image = ImageIO.read(getClass().getResource("0.png"));
			width = image.getWidth();
			height = image.getHeight();

			x = 132;
			y = 240;
          
			
			size  = 40; // С��ߴ��С
			g = 4;      // ���ٶ�4
			v0 = 20;    // ���ϳ��ٶ�
			t = 0.25;   // ���εļ��
			speed =v0;  // �ѵ�ǰ�ٶȸ�ֵΪ��ʼ�ٶ�
			s=0;        // λ��Ϊ0
			alpha = 0;  // С����нǶ�
		
			images = new BufferedImage [8] ;
			
			for(int i=0;i<8;i++){
				images[i] = ImageIO.read(getClass().getResource(i+".png"));
				
			}
			
			index = 0;
		}
     //С���򶯵ķ���
	  public void fly(){
		  index++;
		  image = images[(index / 12) % 8];
	  }	
	//�ϴ����ƶ�
	  public void step(){
	   double vo = speed;
	   s = vo*t -g*t*t/2; //λ�Ƽ���
	   y = y-(int)s;
	   double v = vo-g*t; //�����ƶ�����ٶ�
	   speed = v;         //�ƶ�����ٶȸ�����ʼ�ٶ�
	   
	   alpha = Math.atan(s/8);
	   
	  }
	   //���Ϸɷ���
	   public void   flyup(){
		  speed = v0; 
		   
	  }
	   
	  public boolean hit(Ground ground){
	  //�Ƿ�ײ������  
	  boolean ishit = y+size/2 > ground.y;
	  
	  if(ishit){
		  //���ײ������1.����С���y����
		  y=ground.y-size/2;
		  //����С��ĽǶ�
		  alpha = -3.14159265358979323 / 2;
		  return true;
	   }
	    
	    return false;
	  } 
	   
	  //ײ�������жϷ���
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
	
	
	
  //��д��ͼ����  �Ⱥ�˳���Ӱ����ʾЧ��
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
		g.drawString(score + "", 40-3, 60-3); //��ɫ��Ӱ
		g.drawImage(ground.image,ground.x,ground.y,null);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.rotate(-brid.alpha,brid.x,brid.y);
		g.drawImage(brid.image, brid.x-brid.width/2, brid.y-brid.height/2, null);
		graphics2d.rotate(brid.alpha,brid.x,brid.y);
		
		//���Ҫ�ǿ�ʼ������Ϸ����״̬ ��������
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
		
		//��ʼ������
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
	 * �������� x,y�����ӵ�����λ��
	 * */
	class Clounm{
	
	BufferedImage image ;	
	int x,y;
	int width,height;
	int gap ;  //���¼�϶
	int distance ; //�������ӵľ���
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
	//Columnλ�� �ı�÷�	
	public void step(){
		
		x--;
		// x�ڻ���ʱ��Ҫ��width/2 ����ʧ��ʱ���ڰ�����X��ֵ
		if(x == -width/2){
			x = distance*2 -width/2; //����֮������ 2��245
			y = random.nextInt(218)+132;
		}
		
		
	}
	
	}
}
