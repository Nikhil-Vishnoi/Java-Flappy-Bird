/**
  * Helper class used to store coordinates to calculate collisions
  *
  * @author Nikhil V
  * @since 06/02/21
  */
public class Cord {
  private int x;
  private int y;

  /** Constructor */
  public Cord(int xCord, int yCord) {
    x = xCord;
    y = yCord;
  }
  /** Getter and Setter Methods */
  public int getX() { return x; }
  public int getY() { return y; }
  public void setX(int i) { x = i; }
  public void setY(int i) { y = i; }
  /**
    * Checks if Cordinate is inbetween two other cordinates
    * @precondition c1 is the top left cord and c2 is bottom right cord of box
    *               that I am checking the cord is inbetween
    * @param        c1 Coord of top Left point in box
    * @param        c2 Coord of bottom right point in box
    * @return       true if is isInBetween and false if not inbetween
    */
  public boolean isInBetween(Cord c1, Cord c2) {
    Cord obj = this;

    if((obj.getX() >= c1.getX() && obj.getX() <= c2.getX()) && (obj.getY() >= c1.getY() && obj.getY() <= c2.getY()) )
      return true;
    return false;
  }
  /** Overiding toString Method in Object Class */
  public String toString() {
    return "X: "+getX()+" Y: "+getY();
  }
}
