import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TCalculate extends JFrame{

	public static final int WIDTH = 500 ;
	public static final int HEIGHT = 400;   //定义窗体的SIZE
	
	private JTextField result ;             //输入和显示结果 文本框
	
	private JPanel mainPanel ;              //主板块
	private JPanel numPanel  ;              //数字板面
	private JPanel showPanel ;              //显示板面
	private JPanel operetaPanel;            //运算符板面
	
	private JButton [] numB ;               //数字按钮加一元运算符 按钮集合
	private JButton [] operetaB;            //二元运算符符号按钮集合
	
	
	public TCalculate(String title) {
		// TODO Auto-generated constructor stub
		this.setTitle(title);               //设置窗体title
	
		result = new JTextField("0.0", 22); 
		result.setEditable(false);
		
		
		
		mainPanel = new JPanel();
		operetaPanel = new JPanel();
		showPanel = new JPanel();
		numPanel = new JPanel();
		
		numB = new JButton [12];
		operetaB = new JButton[8];
		
		//添加数字和一元运算符
		for(int i=0;i<9;i++){
		  
			numB [i] = new JButton(new Integer(i+1).toString());    
		}
		
        numB[9] = new JButton("0");
        numB[10] = new JButton("cls");
        numB [11] = new JButton(".");
        
        operetaB [0] = new JButton("+");
        operetaB [1] = new JButton("-");
        operetaB [2] = new JButton("*");
        operetaB [3] = new JButton("/");
        operetaB [4] = new JButton("pow");
        operetaB [5] = new JButton("sqrt");
        operetaB [6] = new JButton("-/+");
        operetaB [7] = new JButton("=");
        
        //添加到数字Panel
        
        numPanel.setLayout(new GridLayout(4, 3, 1, 1));
        for(int i=0;i<12;i++){
        	
        	numPanel.add(numB[i]);
        }
         
        //添加二元运算符
         operetaPanel.setLayout(new GridLayout(4,2,1,1));
         for(int i =0;i<8;i++){
         operetaPanel.add(operetaB[i]);    	   
       }
         
        //将result文本输入框 添加到showPane中
         
         showPanel.setLayout(new BorderLayout());
         showPanel.add(result,BorderLayout.NORTH);
         mainPanel.setLayout(new BorderLayout());
         mainPanel.add(showPanel,BorderLayout.NORTH);
         mainPanel.add(numPanel,BorderLayout.WEST);
         mainPanel.add(operetaPanel,BorderLayout.EAST);
         
         this.add(mainPanel,BorderLayout.CENTER);
	
	     this.setSize(WIDTH, HEIGHT);
	     
	        Toolkit too = Toolkit.getDefaultToolkit() ;       //窗体工具包
	        Dimension screenSize = too.getScreenSize() ;      //获取屏幕尺寸
	        this.setLocation((screenSize.width-WIDTH)/2, (screenSize.height-HEIGHT)/2) ; //设置屏幕位置  
	        System.out.println("screenSize.width :"+screenSize.width+" screenSize.height :"+screenSize.height);  
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;  //设置点击关闭键 关闭
	        this.setResizable(false);   //设置屏幕是否可以自由改变大小
	        this.pack();                //设置屏幕自适应
       
	        
	   //添加监听事件
	   for(int i =0;i<numB.length;i++){
		 
		   numB[i].addActionListener(new ButtonLintener());
	   }
    
	   for(int i=0;i<operetaB.length;i++){
		   
		   operetaB[i].addActionListener(new ButtonLintener());
	   }
	}
	
	
	public class ButtonLintener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
	        System.out.println("ActionEvent : " + ((JButton)arg0.getSource()).getText());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         TCalculate calculate = new TCalculate("计算器");
         calculate.setVisible(true);
	}

}
