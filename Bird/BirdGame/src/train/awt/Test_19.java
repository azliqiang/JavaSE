package train.awt;

import java.text.DecimalFormat;

public class Test_19 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        double x  = 10.00;
        double y  = 3.00;
        double result = x/y;
        DecimalFormat format = new DecimalFormat("###.000");
        format.format(result);
        System.out.println(result);
		
	}

}
