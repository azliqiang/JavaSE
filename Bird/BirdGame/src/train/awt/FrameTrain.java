package train.awt;

import java.util.Scanner;

public class FrameTrain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
  
		  Scanner scanner = new Scanner(System.in);
		  int num = scanner.nextInt();
		  while(num!=0){
			  System.out.print(num%10);	  
			  num/=10;


		  }
    
		  
	}
}
