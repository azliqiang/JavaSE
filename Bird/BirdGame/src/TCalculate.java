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
	public static final int HEIGHT = 400;   //���崰���SIZE
	
	private JTextField result ;             //�������ʾ��� �ı���
	
	private JPanel mainPanel ;              //�����
	private JPanel numPanel  ;              //���ְ���
	private JPanel showPanel ;              //��ʾ����
	private JPanel operetaPanel;            //���������
	
	private JButton [] numB ;               //���ְ�ť��һԪ����� ��ť����
	private JButton [] operetaB;            //��Ԫ��������Ű�ť����
	
	
	public TCalculate(String title) {
		// TODO Auto-generated constructor stub
		this.setTitle(title);               //���ô���title
	
		result = new JTextField("0.0", 22); 
		result.setEditable(false);
		
		
		
		mainPanel = new JPanel();
		operetaPanel = new JPanel();
		showPanel = new JPanel();
		numPanel = new JPanel();
		
		numB = new JButton [12];
		operetaB = new JButton[8];
		
		//������ֺ�һԪ�����
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
        
        //��ӵ�����Panel
        
        numPanel.setLayout(new GridLayout(4, 3, 1, 1));
        for(int i=0;i<12;i++){
        	
        	numPanel.add(numB[i]);
        }
         
        //��Ӷ�Ԫ�����
         operetaPanel.setLayout(new GridLayout(4,2,1,1));
         for(int i =0;i<8;i++){
         operetaPanel.add(operetaB[i]);    	   
       }
         
        //��result�ı������ ��ӵ�showPane��
         
         showPanel.setLayout(new BorderLayout());
         showPanel.add(result,BorderLayout.NORTH);
         mainPanel.setLayout(new BorderLayout());
         mainPanel.add(showPanel,BorderLayout.NORTH);
         mainPanel.add(numPanel,BorderLayout.WEST);
         mainPanel.add(operetaPanel,BorderLayout.EAST);
         
         this.add(mainPanel,BorderLayout.CENTER);
	
	     this.setSize(WIDTH, HEIGHT);
	     
	        Toolkit too = Toolkit.getDefaultToolkit() ;       //���幤�߰�
	        Dimension screenSize = too.getScreenSize() ;      //��ȡ��Ļ�ߴ�
	        this.setLocation((screenSize.width-WIDTH)/2, (screenSize.height-HEIGHT)/2) ; //������Ļλ��  
	        System.out.println("screenSize.width :"+screenSize.width+" screenSize.height :"+screenSize.height);  
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;  //���õ���رռ� �ر�
	        this.setResizable(false);   //������Ļ�Ƿ�������ɸı��С
	        this.pack();                //������Ļ����Ӧ
       
	        
	   //��Ӽ����¼�
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
         TCalculate calculate = new TCalculate("������");
         calculate.setVisible(true);
	}

}
