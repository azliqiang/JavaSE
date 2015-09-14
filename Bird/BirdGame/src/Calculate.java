import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
  
public class Calculate extends JFrame {  
  
    public static final int WIDTH = 500 ;  
    public static final int HEIGHT = 400 ;   //�����С
      
    private JTextField result;               //�ı������ 
      
    private JPanel mainPane; //�����   
    private JPanel numPane; //�������   
    private JPanel operatePane; //���������   
    private JPanel showPane; //��ʾ���   
      
    private JButton[] numB; //���ְ�ť   
    private JButton[] operateB; //���������   
      
    public Calculate(String title){  
          
        setTitle(title) ;                                //���ñ���
          
        result = new JTextField("0.0",22) ;        
        result.setEditable(false) ;                      //�ı��Ƿ���Ա༭     
          
        mainPane = new JPanel() ;                        
        numPane = new JPanel() ;                         //���ּ� 0 cls . 1-9 ��ť
        operatePane = new JPanel() ;                     //���������
        showPane = new JPanel() ;                        //��ʾ���
          
        numB = new JButton[12] ;  
        for(int i=0; i<9; i++)   
        {   
            numB[i] = new JButton(new Integer(i+1).toString());   
        }   
        numB[9] = new JButton("0");   
        numB[10] = new JButton("cls");   
        numB[11] = new JButton(".");   
        //�����ְ�ť�ӵ����������  
        numPane.setLayout(new GridLayout(4,3,1,1)) ;  //�������� �м��и�1���������
        for(int i=0;i<12;i++)  
        	//�������
            numPane.add(numB[i]) ;  				  //�Ѱ�ť��ӵ�Jpanel //��ӵ�������
          
        operateB = new JButton[8] ;  
        operateB[0] = new JButton("+");   
        operateB[1] = new JButton("-");   
        operateB[2] = new JButton("*");   
        operateB[3] = new JButton("/");   
        operateB[4] = new JButton("pow");   
        operateB[5] = new JButton("sqrt");   
        operateB[6] = new JButton("+/-");   
        operateB[7] = new JButton("=");  
        //�Ѳ�������ť��ӵ����������  
        operatePane.setLayout(new GridLayout(4,2,1,1)) ;  //4��2�� ��Ӳ�������
        for(int i=0; i<8; i++)   
            operatePane.add(operateB[i]) ;                //��Ӳ�������
          
        showPane.setLayout(new BorderLayout()) ;          //���bord���� �ֶ����ϱ���
        showPane.add(result, BorderLayout.NORTH);         //����ʾ����ӵ��ϱ�
          
        mainPane.setLayout(new BorderLayout()) ;          //�ڶ���һ��Panel 
        mainPane.add(showPane, BorderLayout.NORTH);       //��ʾҳ���panel��ӵ�mainpanel�ı���
        mainPane.add(numPane, BorderLayout.WEST);         //���������ӵ� ����
        mainPane.add(operatePane, BorderLayout.EAST);     //���������ӵ� ����
          
        this.add(mainPane, BorderLayout.CENTER);          //����ҳ����ӵ��м�λ��
        this.setSize(WIDTH, HEIGHT);                      //����ƽ��size
          
       
          
        Toolkit too = Toolkit.getDefaultToolkit() ;       //���幤�߰�
        Dimension screenSize = too.getScreenSize() ;      //��ȡ��Ļ�ߴ�
        this.setLocation((screenSize.width-WIDTH)/2, (screenSize.height-HEIGHT)/2) ; //������Ļλ��  
        System.out.println("screenSize.width :"+screenSize.width+" screenSize.height :"+screenSize.height);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;  //���õ���رռ� �ر�
        this.setResizable(false);   //������Ļ�Ƿ�������ɸı��С
        this.pack();                //������Ļ����Ӧ
        
        //���ð�ť�����¼�
        ButtonListener button = new ButtonListener() ;  
        for(int i = 0; i < numB.length; i++)  
            numB[i].addActionListener(button) ;  
        for(int i=0;i<operateB.length;i++)  
            operateB[i].addActionListener(button) ;  
          
    }  
  
    class ButtonListener implements ActionListener   
    {   
        private String last;            //�洢��һ�˲�����   
        private String strVal;          //�洢���ֶ�Ӧ���ַ���   
        private double total;       	//����   
        private double number;     		//�洢���������   
        private boolean firsttime; 		//�ж��Ƿ��һ�ΰ��µ��ǲ�������ť   
        private boolean operatorPressed;//�ж��Ƿ��Ѿ�������������ť   
  
        public ButtonListener()   
        {   
            firsttime = true;   
            strVal = "";   
        }  
      
        //�¼�����  
        public void actionPerformed(ActionEvent e){  
          
            String str = ((JButton)e.getSource()).getText().trim() ;  
            System.out.println("str :" +str +" str.charAt(0) :" +str.charAt(0));
            if(Character.isDigit(str.charAt(0)))  //�ж��Ƿ�����
                handleNumber(str) ;  //��������� 
            else  
                calculate(str) ;     //������߼������
          
        }  
      
        //�ж���һԪ���������Ƕ�Ԫ�������������ݲ���������������   
        public void calculate(String op)   
        {   
            operatorPressed = true;   //�Ƿ��Ѿ�����������
              //��һ�����������     �Ƿ���һԪ������
            if(firsttime && !isUnary(op))   
            {   
            	//����
                total = getNumberOnDisplay();   
                firsttime = false;   
            }   
            if(isUnary(op))   
            {   //�����һԪ�����
                handleUnaryOp(op);   
            }   
            else if(last != null)   
            {   
                handleBinaryOp(last);   
            }   
            if(! isUnary(op))   
            {   
                last = op;   
            }   
        }   
      
        //�ж��Ƿ�һԪ������   
        public boolean isUnary(String s)   
        {   
            return s.equals("=") || s.equals("cls") || s.equals("sqrt")   
                                    || s.equals("+/-") || s.equals(".");   
        }   
  
        //����һԪ������   
        public void handleUnaryOp(String op)   
        {   
            if(op.equals("+/-"))   
            {  // number�����������
                number = negate(getNumberOnDisplay() + "");   
                result.setText("");   
                result.setText(number + "");   
                return;   
            }  
            else if(op.equals("."))   
            {   
                handleDecPoint();   
                return;   
            }  
            else if(op.equals("sqrt"))   
            {   
                number = Math.sqrt(getNumberOnDisplay());   
                result.setText("");   
                result.setText(number + "");   
                return;   
            }  
            else if(op.equals("="))   
            {//   
                if(last!= null && !isUnary(last))   
                {   
                    handleBinaryOp(last);   
                }   
                last = null;   
                firsttime = true;   
                return;   
            }  
            else   
            {   
                clear();   
            }   
        }   
  
        //�����Ԫ�����   
        public void handleBinaryOp(String op)   
        {   
            if(op.equals("+"))   
            {   
                total +=number;   
            }  
            else if(op.equals("-"))   
            {   
                total -=number;   
            }  
            else if(op.equals("*"))   
            {   
                total *=number;   
            }  
            else if(op.equals("/"))   
            {   
                try   
                {   
                    total /=number;   
                }catch(ArithmeticException ae){}   
            }  
            else if(op.equals("pow"))   
                total = Math.pow(total, number);   
            //result.setText("");   
            last = null;   
            // strVal = "";   
            number = 0;   
            result.setText(total + "");   
        }   
  
        //�÷������ڴ������ְ�ť   
        public void handleNumber(String s)   
        {   
            if(!operatorPressed)   
            {   
                strVal += s;   
            }  
            else   
            {   
                operatorPressed = false;   
                strVal = s;   
            }   
           
            number = new Double(strVal).doubleValue();   
            result.setText("");   
            result.setText(strVal);   
        }   
  
        //�÷������ڰ���"."��ť   
        public void handleDecPoint()   
        {   
            operatorPressed = false;   
       
            if(strVal.indexOf(".")<0)   
            {   
                strVal += ".";   
            }   
            result.setText("");   
            result.setText(strVal);   
        }   
  
        //�÷������ڽ�һ������   
        public double negate(String s)   
        {   
            operatorPressed = false;   
            //�����һ��������ȥ��С��������0   
            if(number == (int)number)   
            {   
                s = s.substring(0,s.indexOf("."));   
            }   
  
            //�����"-"�����ڸ�����ǰ��   
            if(s.indexOf("-")<0)   
            {   
                strVal = "-" + s;   
            }   
            else   
            {   
                strVal = s.substring(1);   
            }   
            return new Double(strVal).doubleValue();   
        }   
  
        //����ʾ���е�ֵת����Double   
        public double getNumberOnDisplay()   
        {   
            return new Double(result.getText()).doubleValue();   
        }   
  
        //�����Ļ���������еı�ʶ   
        public void clear()   
        {   
            firsttime = true;   
            last = null;   
            strVal = "";   
            total = 0;   
            number = 0;   
            result.setText("0");   
        }   
    }  
  
    public static void main(String[] args) {   
        Calculate c = new Calculate("�򵥵ļ���������");   
        c.setVisible(true);   
    }   
}  