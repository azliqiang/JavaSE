package train.awt;

import java.util.Scanner;

public class ShopTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int z =0;z<10;z++){
		int index = 7;
		
		Scanner scanner = new Scanner(System.in);
		Integer i = scanner.nextInt();
		System.out.println("购物金额是：" + i);
		System.out.println("是否参加购物活动");
		System.out.println("1.满50  +1元可以换航空母舰");
		System.out.println("2.满100 +2元可以换媳妇");
		System.out.println("3.满100 +3元可以换一只狗");
		System.out.println("4.满200 +4元可以换一个面包");
		System.out.println("5.满200 +5元可以换一驴");
		System.out.println("6.不参加活动");

		if (i <= 100 && i >= 50) {
			Scanner scanner2 = new Scanner(System.in);
			Integer result = scanner2.nextInt();
            if(result == 1){
            	index = 1;
            	i+=1;
            }else if(result == 6){
            	index =6;
            }
            
		} else if (i <= 200 && i>100) {
			
			Scanner scanner2 = new Scanner(System.in);
			Integer result = scanner2.nextInt();
            if(result == 1){
            	index = 1;
            	i+=1;
            }else if(result == 6){
            	index =6;
            }else if(result == 2){
            	index =2;
            	i+=2;
            }else if(result ==3 ){
            	index =3;
            	i+=3;
            }

		} else if (i > 200) {
			
			Scanner scanner2 = new Scanner(System.in);
			Integer result = scanner2.nextInt();
            if(result == 1){
            	index = 1;
            	i+=1;
            }else if(result == 6){
            	index =6;
            }else if(result == 2){
            	index =2;
            	i+=2;
            }else if(result ==3 ){
            	index =3;
            	i+=3;
            }else if(result == 4){
            	index =4;
            	i+=4;
            }else if(result == 5){
            	index =5;
            	i+=5;
            }

		}else{
			System.out.println("消费金额:" + i);
			System.out.println("不足50 没法参与换购活动");
			
		}
   
		if(index == 6){
			System.out.println("消费金额:" + i);
			System.out.println("没有参加换购活动" + i);
		}else if(index == 1){
			System.out.println("消费金额:" + i);
			System.out.println("成功换购了一个航空母舰");
		}else if(index == 2){
			System.out.println("消费金额:" + i);
			System.out.println("成功换购了一个媳妇");
		}else if(index == 3){
			System.out.println("消费金额:" + i);
			System.out.println("成功换购了一个狗");
		}else if(index == 4){
			System.out.println("消费金额:" + i);
			System.out.println("成功换购了一个面包");
		}else if(index == 5){
			System.out.println("消费金额:" + i);
			System.out.println("成功换购了一个驴");
		}else if(index == 7){
			System.out.println("消费金额:" + i);
			System.out.println("兑换失败！兑换条件与选项不服！");
		}
		
		}
	}

}
