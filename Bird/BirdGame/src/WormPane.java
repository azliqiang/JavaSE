import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class WormPane {
  private Worm worm;
  /** 行数 */
  private int rows = 10;
  /** 列数 */
  private int cols = 32;
  
  /** 食物 */
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
      foods.add(food);//不会重复添加
      if(foods.size()==n){
        break;
      }
    }
  }
  
  public Worm getWorm() {
    return worm;
  }
  
  /** 画出当前面板 */
  public void print(){
    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        if(i==0||i==rows-1){
          System.out.print("-");//不能输出回车
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
      System.out.println(); //一行结束以后画回车
    }
  }
  
  public class Worm {
    //<Node> 约束了集合中元素的类型, nodes中只能放置Node实例
    private LinkedList<Node> nodes = 
      new LinkedList<Node>();
    //当前默认的行走方向
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
      dir = RIGHT;//默认向右走
    }
    public Worm(LinkedList<Node> nodes) {
      this.nodes.clear();
      this.nodes.addAll(nodes);//复制了集合的内容
      //this.nodes=nodes;//使用同一个集合对象
    }
    /** 走一步 */
    public void step(){
      //找到头节点
      Node head = nodes.getFirst();//相当于:get(0)    
      //根据当前方向计算新节点
      int i = head.getI() + dir/10;
      int j = head.getJ() + dir%10;
      head = new Node(i,j);
      //插入新节点到头部
      nodes.addFirst(head);//相当于:add(0, head)
      if(foods.remove(head)){
        //如果删除成功返回true,表示吃掉了一个食物 
        return;
      }
      //删除末尾节点
      nodes.removeLast();//相当于:remove(nodes.size()-1)
    }
    /** 换个方向走一步 */
    public void step(int dir){
      if(this.dir+dir==0){
        throw new RuntimeException("不能掉头行驶!");
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
