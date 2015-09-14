package train.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
 
import javax.swing.*;
 /**
  * 2048 游戏
  * 
  * anmin/zwf
  * 
  * */
@SuppressWarnings("serial")
public class TextClass extends JFrame{
 
    /**
     * @param args
     */
    Container thisContainer; 
    JLabel label;
    JPanel panel1,panel2,panel3;
//  JPanel panel3 = new JPanel();
    JButton numBtn[][]=new JButton[4][4];
    JButton controlBtn[]=new JButton[4];
    JButton playAgainButton,exitButton;
    int grid[][]=new int[4][4],score=0;
    Action_2048 act=new Action_2048();
     
    public void Init(){
        this.setBounds(280,100,500,450);
        this.setTitle("Java版2048");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        label=new JLabel("Score:");
        label.setForeground(Color.red);
        thisContainer=this.getContentPane();
        panel1=new JPanel();
        panel2=new JPanel();
        panel3=new JPanel();
        thisContainer.add(panel1,BorderLayout.NORTH); 
        thisContainer.add(panel2,BorderLayout.CENTER);
        thisContainer.add(panel3,BorderLayout.SOUTH);
         
        panel1.setLayout(new FlowLayout());
        panel1.add(label);
         
        //对数组面板panel2布局
        panel2.setLayout(new GridLayout(4,4));
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                numBtn[i][j]=new JButton();
                grid[i][j]=0;
                numBtn[i][j]=new JButton(String.valueOf(grid[i][j]));
                numBtn[i][j].setFont(new java.awt.Font("Segoe UI Symbol",1, 25));
//              设置背景色
                numBtn[i][j].setBackground(Color.CYAN);
//              设置按钮不要边框
//              numBtn[i][j].setBorderPainted(false);
                //设置凸起来的按钮，很多其他的swing也可用此方法
                numBtn[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
//              设置凹起来的按钮，很多其他的swing也可用此方法
//              numBtn[i][j].setBorder(BorderFactory.createLoweredBevelBorder());
                panel2.add(numBtn[i][j]);
            }
        }
        int k=(int)Math.round(Math.random()*15);
        int i=k/4;
        int j=k%4;
        grid[i][j]=2;
        numBtn[i][j].setText(String.valueOf(grid[i][j]));
         
        //对方向控制按钮进行布局以及对"退出"、"再来一局"进行布局
        panel3.setLayout(new FlowLayout());
        controlBtn[0] = new JButton("↑");
        controlBtn[1] = new JButton("↓");
        controlBtn[2] = new JButton("←");
        controlBtn[3] = new JButton("→");
         
        for(i=0;i<4;i++){
            controlBtn[i].addActionListener(act);
            panel3.add(controlBtn[i]);
        }
        playAgainButton=new JButton("再来一局"); 
        exitButton=new JButton("" +
        		"" +
        		"退出");
        playAgainButton.addActionListener(act);
        exitButton.addActionListener(act);
        panel3.add(exitButton);
        panel3.add(playAgainButton);
         
 
        this.setVisible(true);
    }
    class Action_2048 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            int f=0,TongGuanF=0;
            //"↑"————controlBtn[0]
            if(e.getSource()==controlBtn[0]){
                int i=0,j=0,n,t[]={0,0,0,0};
                int b[][]=new int[4][4];
                for(i=0;i<4;i++)
                {
                    n=0;
                    for(j=0;j<4;j++)
                    {
                        b[j][i]=grid[j][i];
                        if(grid[j][i]!=0)
                        {
                            t[n++]=grid[j][i];
                        }
                    }
                    for(j=0;j<4;j++)
                    {
                        grid[j][i]=t[j];
                        numBtn[j][i].setText(String.valueOf(grid[j][i]));
                        t[j]=0;     //避免下一列受上一列的影响
                    }
                }
                for(i=0;i<4;i++)
                {
                    for(j=0;j<4;j++)
                    {
                        if(grid[i][j]!=b[i][j])
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==1)
                        break;
                }
 
 
                for(i=0;i<4;i++)
                {
                    for(j=0;j<3;j++)
                    {
                        if((grid[j][i]==grid[j+1][i])&&(grid[j][i]!=0))
                        {
                            f=1;
                            grid[j][i]=2*grid[j][i];
                            numBtn[j][i].setText(String.valueOf(grid[j][i]));
                            score+=grid[j][i];
                            for(n=j+1;n<3;n++)
                            {
                                grid[n][i]=grid[n+1][i];
                                numBtn[n][i].setText(String.valueOf(grid[n][i]));
                            }
                            grid[n][i]=0;
                            numBtn[n][i].setText(String.valueOf(grid[n][i]));
                        }
                    }
                }
            }
            if(e.getSource()==controlBtn[1]){
                int i=0,j=0,n,t[]={0,0,0,0};
                int b[][]=new int[4][4];
                for(i=0;i<4;i++)
                {
                    n=0;
                    for(j=0;j<4;j++)
                    {
                        b[j][i]=grid[j][i];
                        if(grid[j][i]!=0)
                        {
                            t[n++]=grid[j][i];
                        }
                    }
                    for(j=3;j>=0;j--)
                    {
                        if(n>0)
                        {
                            grid[j][i]=t[n-1];
                            numBtn[j][i].setText(String.valueOf(grid[j][i]));
                            n--;
                        }else{
                            grid[j][i]=0;       //避免下一列受上一列的影响
                            numBtn[j][i].setText(String.valueOf(grid[j][i]));
                        }
                    }
                }
                for(i=0;i<4;i++)
                {
                    for(j=0;j<4;j++)
                    {
                        if(grid[i][j]!=b[i][j])
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==1)
                        break;
                }
 
 
                for(i=0;i<4;i++)
                {
                    for(j=3;j>0;j--)
                    {
                        if((grid[j][i]==grid[j-1][i])&&(grid[j][i]!=0))
                        {
                            f=1;
                            grid[j][i]=2*grid[j][i];
                            numBtn[j][i].setText(String.valueOf(grid[j][i]));
                            score+=grid[j][i];
                            for(n=j-1;n>0;n--)
                            {
                                grid[n][i]=grid[n-1][i];
                                numBtn[n][i].setText(String.valueOf(grid[n][i]));
                            }
                            grid[n][i]=0;
                            numBtn[n][i].setText(String.valueOf(grid[n][i]));
                        }
                    }
                }
            }
            if(e.getSource()==controlBtn[2]){
                int i=0,j=0,n,t[]={0,0,0,0};
                int b[][]=new int[4][4];
                for(i=0;i<4;i++)
                {
                    n=0;
                    for(j=0;j<4;j++)
                    {
                        b[i][j]=grid[i][j];
                        if(grid[i][j]!=0)
                        {
                            t[n++]=grid[i][j];
                        }
                    }
                    for(j=0;j<4;j++)
                    {
                        grid[i][j]=t[j];
                        numBtn[i][j].setText(String.valueOf(grid[i][j]));
                        t[j]=0;     //避免下一行受上一行的影响
                    }
                }
                for(i=0;i<4;i++)
                {
                    for(j=0;j<4;j++)
                    {
                        if(grid[i][j]!=b[i][j])
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==1)
                        break;
                }
 
 
                for(i=0;i<4;i++)
                {
                    for(j=0;j<3;j++)
                    {
                        if((grid[i][j]==grid[i][j+1])&&(grid[i][j]!=0))
                        {
                            f=1;
                            grid[i][j]=grid[i][j]*2;
                            numBtn[i][j].setText(String.valueOf(grid[i][j]));
                            score+=grid[i][j];
                            for(n=j+1;n<3;n++)
                            {
                                grid[i][n]=grid[i][n+1];
                                numBtn[i][n].setText(String.valueOf(grid[i][n]));
                            }
                            grid[i][n]=0;
                            numBtn[i][n].setText(String.valueOf(grid[i][n]));
                        }
                    }
                }
            }
            if(e.getSource()==controlBtn[3]){
                int i=0,j=0,n,t[]={0,0,0,0};
                int b[][]=new int[4][4];
                for(i=0;i<4;i++)
                {
                    n=0;
                    for(j=0;j<4;j++)
                    {
                        b[i][j]=grid[i][j];
                        if(grid[i][j]!=0)
                        {
                            t[n++]=grid[i][j];
                        }
                    }
                    for(j=3;j>=0;j--)
                    {
                        if(n>0)
                        {
                            grid[i][j]=t[n-1];
                            n--;
                            t[n]=0;
                            numBtn[i][j].setText(String.valueOf(grid[i][j]));
                        }else{
                            grid[i][j]=0;
                            numBtn[i][j].setText(String.valueOf(grid[i][j]));
                        }
                         
                        //避免下一行受上一行的影响
                    }
                }
                for(i=0;i<4;i++)
                {
                    for(j=0;j<4;j++)
                    {
                        if(grid[i][j]!=b[i][j])
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==1)
                        break;
                }
 
 
                for(i=0;i<4;i++)
                {
                    for(j=3;j>0;j--)
                    {
                        if((grid[i][j]==grid[i][j-1])&&(grid[i][j]!=0))
                        {
                            f=1;
                            grid[i][j]=grid[i][j]*2;
                            numBtn[i][j].setText(String.valueOf(grid[i][j]));
                            score+=grid[i][j];
                            for(n=j-1;n>0;n--)
                            {
                                grid[i][n]=grid[i][n-1];
                                numBtn[i][n].setText(String.valueOf(grid[i][n]));
                            }
                            grid[i][n]=0;
                            numBtn[i][n].setText(String.valueOf(grid[i][n]));
                        }
                    }
                }
            }
            if(e.getSource()==playAgainButton){
                for(int i=0;i<4;i++){
                    for(int j=0;j<4;j++){
                        grid[i][j]=0;
                        numBtn[i][j].setText(String.valueOf(grid[i][j]));
                    }
                }
                int k=(int)Math.round(Math.random()*15);
                int i=k/4;
                int j=k%4;
                grid[i][j]=2;
                numBtn[i][j].setText(String.valueOf(grid[i][j]));
            }
            if(e.getSource()==exitButton){
                System.exit(0);
            }
             
            //产生新数2、4
            if(f==1){
                int r[]=new int[16];
                int n=0,i,j;
                for(i=0;i<4;i++)
                {
                    for(j=0;j<4;j++)
                    {
                        if((grid[i][j]==0))
                        {
                            r[n]=i*4+j;
                            n++;
                        }
                    }
                }
 
                Random rd=new Random();
                int k=rd.nextInt(n);
                i=r[k]/4;
                j=r[k]%4;
                if((i+j)%5!=0)
                {
                    grid[i][j]=2;
                }
                else{
                    grid[i][j]=4;
                }
                numBtn[i][j].setText(String.valueOf(grid[i][j]));
                if(score>5000){
                    for(i=0;i<4;i++){
                        for(j=0;j<4;j++){
                            if(grid[i][j]==2048)
                                TongGuanF=1;
                        }
                    }
                }
                if(TongGuanF==1)
                    label.setText("恭喜过关！");
                else
                    label.setText("Score:  "+String.valueOf(score)+"分"); 
            }
        }
         
    }
     
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TextClass f=new TextClass();
        f.Init();
    }
 
}