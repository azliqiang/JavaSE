import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class WormPane {
  private Worm worm;
  /** ���� */
  private int rows = 10;
  /** ���� */
  private int cols = 32;
  
  /** ʳ�� */
  private Set<Node> foods = new HashSet<Node>();
  
  public WormPane() {
    worm = new Worm();
    initFoods(5);
  }
  
  public void initFoods(int n){
    Random r = new Random();
    while(true){
      int i = r.nextInt(rows-2)+1;
      int j = r.nextInt(cols-2)+1; //sui ji i j zuobiaodian 
      
      if(worm.contains(i, j)){   //ruguo yijing cunzai bu chuangjian ciayihuihe 
        continue;
      }
      Node food = new Node(i,j);
      //if(foods.contains(food)){
       // continue;
      //}
      foods.add(food);//�����ظ����
      if(foods.size()==n){
        break;
      }
    }
  }
  
  public Worm getWorm() {
    return worm;
  }
  
  /** ������ǰ��� */
  public void print(){
    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        if(i==0||i==rows-1){
          System.out.print("-");//��������س�
        }else if(j==0||j==cols-1){
          System.out.print("|");
        }else if(worm.contains(i, j)){
          System.out.print("#");
        }else if(foods.contains(new Node(i, j))){
          System.out.print("0");
        }else{
          System.out.print(" ");
        }
      }
      System.out.println(); //һ�н����Ժ󻭻س�
    }
  }
  
  public class Worm {
    //<Node> Լ���˼�����Ԫ�ص�����, nodes��ֻ�ܷ���Nodeʵ��
    private LinkedList<Node> nodes = 
      new LinkedList<Node>();
    //��ǰĬ�ϵ����߷���
    private int dir;
    
    public static final int UP = -10;
    public static final int DOWN = 10;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    
    
    public Worm() {
      nodes.add(new Node(3,9));
      nodes.add(new Node(4,9));
      nodes.add(new Node(5,9));
      nodes.add(new Node(5,10));
      nodes.add(new Node(5,11));
      nodes.add(new Node(6,11));
      nodes.add(new Node(7,11));
      dir = RIGHT;//Ĭ��������
    }
    public Worm(LinkedList<Node> nodes) {
      this.nodes.clear();
      this.nodes.addAll(nodes);//�����˼��ϵ�����
      //this.nodes=nodes;//ʹ��ͬһ�����϶���
    }
    /** ��һ�� */
    public void step(){
      //�ҵ�ͷ�ڵ�
      Node head = nodes.getFirst();//�൱��:get(0)    
      //���ݵ�ǰ��������½ڵ�
      int i = head.getI() + dir/10;
      int j = head.getJ() + dir%10;
      head = new Node(i,j);
      //�����½ڵ㵽ͷ��
      nodes.addFirst(head);//�൱��:add(0, head)
      if(foods.remove(head)){
        //���ɾ���ɹ�����true,��ʾ�Ե���һ��ʳ�� 
        return;
      }
      //ɾ��ĩβ�ڵ�
      nodes.removeLast();//�൱��:remove(nodes.size()-1)
    }
    /** ����������һ�� */
    public void step(int dir){
      if(this.dir+dir==0){
        throw new RuntimeException("���ܵ�ͷ��ʻ!");
      }
      this.dir = dir;
      step();
    }
    public boolean contains(int i, int j) {
//      for(int k=0; k<nodes.size(); k++){
//        Node n = nodes.get(i);
//        if(n.getI()==i && n.getJ()==j){
//          return true;
//        }
//      }
//      return false;
      return nodes.contains(new Node(i,j));
    }
    @Override
    public String toString() {
      return nodes.toString();
    }
  }  
  
}
