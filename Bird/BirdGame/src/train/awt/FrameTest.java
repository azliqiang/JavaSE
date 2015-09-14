package train.awt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;

public class FrameTest extends Canvas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         JFrame frame = new JFrame("this is a jframe");
         
		
		 frame.setLayout(new BorderLayout());
		 frame.add(new Button(),"North");
		 frame.setSize(500,500);
		 frame.setBackground(Color.RED);
		 frame.setVisible(true);
	}
  
}
