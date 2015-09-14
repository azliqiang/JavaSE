import java.util.Scanner;
 

public class WormDemo {
  public static void main(String[] args) {
    final WormPane pane = new WormPane();
    final WormPane.Worm worm = pane.getWorm();
    
    Scanner s = new Scanner(System.in);
    while(true){
      pane.print();
      System.out.println(worm); 
      String dir = s.nextLine();
      if(dir.equalsIgnoreCase("u")){
        worm.step(WormPane.Worm.UP);
      }else if(dir.equalsIgnoreCase("d")){
        worm.step(WormPane.Worm.DOWN);
      }else if(dir.equalsIgnoreCase("l")){
        worm.step(WormPane.Worm.LEFT);
      }else if(dir.equalsIgnoreCase("r")){
        worm.step(WormPane.Worm.RIGHT);
      }else if(dir.equalsIgnoreCase("q")){
        System.out.println("Bye ^_-");
        break;
      }else{
        worm.step();
      }
    }
  }

}
