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
		System.out.println("�������ǣ�" + i);
		System.out.println("�Ƿ�μӹ���");
		System.out.println("1.��50  +1Ԫ���Ի�����ĸ��");
		System.out.println("2.��100 +2Ԫ���Ի�ϱ��");
		System.out.println("3.��100 +3Ԫ���Ի�һֻ��");
		System.out.println("4.��200 +4Ԫ���Ի�һ�����");
		System.out.println("5.��200 +5Ԫ���Ի�һ¿");
		System.out.println("6.���μӻ");

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
			System.out.println("���ѽ��:" + i);
			System.out.println("����50 û�����뻻���");
			
		}
   
		if(index == 6){
			System.out.println("���ѽ��:" + i);
			System.out.println("û�вμӻ����" + i);
		}else if(index == 1){
			System.out.println("���ѽ��:" + i);
			System.out.println("�ɹ�������һ������ĸ��");
		}else if(index == 2){
			System.out.println("���ѽ��:" + i);
			System.out.println("�ɹ�������һ��ϱ��");
		}else if(index == 3){
			System.out.println("���ѽ��:" + i);
			System.out.println("�ɹ�������һ����");
		}else if(index == 4){
			System.out.println("���ѽ��:" + i);
			System.out.println("�ɹ�������һ�����");
		}else if(index == 5){
			System.out.println("���ѽ��:" + i);
			System.out.println("�ɹ�������һ��¿");
		}else if(index == 7){
			System.out.println("���ѽ��:" + i);
			System.out.println("�һ�ʧ�ܣ��һ�������ѡ�����");
		}
		
		}
	}

}
