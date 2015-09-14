package train.awt;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingDemo extends JPanel{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      JFrame frame = new JFrame("练习swing");
      SwingDemo demo = new SwingDemo();
      frame.add(demo);
      Label label = new Label();  //文本显示
      label.setBackground(Color.GRAY);
      label.setText("hello world");
//      label.setLocation(100, 100);
      frame.add(label);
      frame.setSize(600, 600);
      frame.setVisible(true);
      
//		JPanel panel = new JPanel();
//		panel.setSize(200,300);
//		panel.setBackground(Color.BLUE);
//		frame.setSize(600, 600);
//		frame.setLocationRelativeTo(null);    //放在屏幕中间
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(panel);
//		frame.setVisible(true);
//		demo.setSize(600, 600);
//		demo.add(new Button());
//		demo.setVisible(true);
		
         
      
	}

	@Override
	public void paint(Graphics g)  {
		BufferedImage image = null;
      try {
		image = ImageIO.read(getClass().getResource("0.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
		
      
      g.drawImage(image,0,0,null);
//      g.clipRect(50, 50, 90, 40);
      g.drawRect(100, 100, 60, 120);
      
	}
	
}
