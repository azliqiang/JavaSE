package train.awt;

import java.awt.Graphics;
import java.awt.Panel;
import java.util.LinkedList;
import java.util.List;


public class Test {

	int index = 0;
	
	@SuppressWarnings("null")
	public  void mian(String [] args) {
		// TODO Auto-generated method stub
//
		
		String [] date =null ;
		date[0] ="0";
		date[1]="1";
		date[2]="2";
		date[3]="2";
		date[4]="4";
		date[5]="3";
		date[6]="3";
		date[7]="4";
		date[8]="1";
		
		for(int i =0;i<date.length;i++){
			for(int j=date.length-1;j>i;j--){
				
				if(date[i] == date[j] && date[i]!= null){
					 date[j] = null;
				}
				
			}
		}
		
		for(int z = 0;z<date.length;z++){
			System.out.println("Z :"+z+" :"+ date[z]);
		}
		
	}
	
}
