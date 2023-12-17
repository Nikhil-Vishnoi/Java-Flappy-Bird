/**
  * Used to draw bird and move it up and down
  *
  * @author Nikhil Vishnoi
  * @since 06/02/21
  */
public class Bird {
  private int x;
  private int y;
  private boolean flapping;
  public Bird(int xPos, int yPos) {
    x = xPos;
    y = yPos;
    flapping = false;
  }
  public Bird() {
    this(GameConstants.factor,GameConstants.height/2);
  }
  /** Action Methods */
  public void jump() {
    if(y < GameConstants.height && y > (-1)*GameConstants.factor/2 ){
      y -= GameConstants.jumpMult;
    }else{
      y = y;
    }

  }
  public void fall() {
    y += GameConstants.grav;
  }
  /** Accesser Methods */
  public int getX() { return x; }
  public int getY() { return y; }
  /** Setter method */
  public void setY(int i) { y = i; }

}
