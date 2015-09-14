import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
  
public class Calculate extends JFrame {  
  
    public static final int WIDTH = 500 ;  
    public static final int HEIGHT = 400 ;   //窗体大小
      
    private JTextField result;               //文本输入框 
      
    private JPanel mainPane; //主面板   
    private JPanel numPane; //数字面板   
    private JPanel operatePane; //操作符面板   
    private JPanel showPane; //显示面板   
      
    private JButton[] numB; //数字按钮   
    private JButton[] operateB; //操作符面板   
      
    public Calculate(String title){  
          
        setTitle(title) ;                                //设置标题
          
        result = new JTextField("0.0",22) ;        
        result.setEditable(false) ;                      //文本是否可以编辑     
          
        mainPane = new JPanel() ;                        
        numPane = new JPanel() ;                         //数字加 0 cls . 1-9 按钮
        operatePane = new JPanel() ;                     //操作符面板
        showPane = new JPanel() ;                        //显示面板
          
        numB = new JButton[12] ;  
        for(int i=0; i<9; i++)   
        {   
            numB[i] = new JButton(new Integer(i+1).toString());   
        }   
        numB[9] = new JButton("0");   
        numB[10] = new JButton("cls");   
        numB[11] = new JButton(".");   
        //将数字按钮加到数字面板上  
        numPane.setLayout(new GridLayout(4,3,1,1)) ;  //四行三列 中间有个1个网格距离
        for(int i=0;i<12;i++)  
        	//添加数字
            numPane.add(numB[i]) ;  				  //把按钮添加到Jpanel //添加到画布上
          
        operateB = new JButton[8] ;  
        operateB[0] = new JButton("+");   
        operateB[1] = new JButton("-");   
        operateB[2] = new JButton("*");   
        operateB[3] = new JButton("/");   
        operateB[4] = new JButton("pow");   
        operateB[5] = new JButton("sqrt");   
        operateB[6] = new JButton("+/-");   
        operateB[7] = new JButton("=");  
        //把操作符按钮添加到操作面板上  
        operatePane.setLayout(new GridLayout(4,2,1,1)) ;  //4行2列 添加操作符号
        for(int i=0; i<8; i++)   
            operatePane.add(operateB[i]) ;                //添加操作符号
          
        showPane.setLayout(new BorderLayout()) ;          //添加bord布局 分东西南北中
        showPane.add(result, BorderLayout.NORTH);         //讲显示框添加到上边
          
        mainPane.setLayout(new BorderLayout()) ;          //在定义一个Panel 
        mainPane.add(showPane, BorderLayout.NORTH);       //显示页面的panel添加到mainpanel的北部
        mainPane.add(numPane, BorderLayout.WEST);         //数字面板添加到 西部
        mainPane.add(operatePane, BorderLayout.EAST);     //把运算符添加到 东部
          
        this.add(mainPane, BorderLayout.CENTER);          //将主页面添加到中间位置
        this.setSize(WIDTH, HEIGHT);                      //设置平面size
          
       
          
        Toolkit too = Toolkit.getDefaultToolkit() ;       //窗体工具包
        Dimension screenSize = too.getScreenSize() ;      //获取屏幕尺寸
        this.setLocation((screenSize.width-WIDTH)/2, (screenSize.height-HEIGHT)/2) ; //设置屏幕位置  
        System.out.println("screenSize.width :"+screenSize.width+" screenSize.height :"+screenSize.height);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;  //设置点击关闭键 关闭
        this.setResizable(false);   //设置屏幕是否可以自由改变大小
        this.pack();                //设置屏幕自适应
        
        //设置按钮监听事件
        ButtonListener button = new ButtonListener() ;  
        for(int i = 0; i < numB.length; i++)  
            numB[i].addActionListener(button) ;  
        for(int i=0;i<operateB.length;i++)  
            operateB[i].addActionListener(button) ;  
          
    }  
  
    class ButtonListener implements ActionListener   
    {   
        private String last;            //存储上一此操作符   
        private String strVal;          //存储数字对应的字符串   
        private double total;       	//总数   
        private double number;     		//存储新输入的数   
        private boolean firsttime; 		//判断是否第一次按下的是操作符按钮   
        private boolean operatorPressed;//判断是否已经按过操作符按钮   
  
        public ButtonListener()   
        {   
            firsttime = true;   
            strVal = "";   
        }  
      
        //事件处理  
        public void actionPerformed(ActionEvent e){  
          
            String str = ((JButton)e.getSource()).getText().trim() ;  
            System.out.println("str :" +str +" str.charAt(0) :" +str.charAt(0));
            if(Character.isDigit(str.charAt(0)))  //判断是否数字
                handleNumber(str) ;  //如果是数字 
            else  
                calculate(str) ;     //如果是逻辑运算符
          
        }  
      
        //判断是一元操作符还是二元操作符，并根据操作符类型做计算   
        public void calculate(String op)   
        {   
            operatorPressed = true;   //是否已经按过操作符
              //第一次输入操作符     是否是一元操作符
            if(firsttime && !isUnary(op))   
            {   
            	//总数
                total = getNumberOnDisplay();   
                firsttime = false;   
            }   
            if(isUnary(op))   
            {   //如果是一元运算符
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
      
        //判断是否一元操作符   
        public boolean isUnary(String s)   
        {   
            return s.equals("=") || s.equals("cls") || s.equals("sqrt")   
                                    || s.equals("+/-") || s.equals(".");   
        }   
  
        //处理一元操作符   
        public void handleUnaryOp(String op)   
        {   
            if(op.equals("+/-"))   
            {  // number新输入的数字
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
  
        //处理二元运算符   
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
  
        //该方法用于处理数字按钮   
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
  
        //该方法用于按下"."按钮   
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
  
        //该方法用于将一个数求反   
        public double negate(String s)   
        {   
            operatorPressed = false;   
            //如果是一个整数，去掉小数点后面的0   
            if(number == (int)number)   
            {   
                s = s.substring(0,s.indexOf("."));   
            }   
  
            //如果无"-"增加在该数的前面   
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
  
        //将显示框中的值转换成Double   
        public double getNumberOnDisplay()   
        {   
            return new Double(result.getText()).doubleValue();   
        }   
  
        //清除屏幕并设置所有的标识   
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
        Calculate c = new Calculate("简单的计算器程序");   
        c.setVisible(true);   
    }   
}  