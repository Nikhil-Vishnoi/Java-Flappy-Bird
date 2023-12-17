import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
/**
  * JPanel for the flappy Bird that uses timers, action listeners and helper
  * classes to create a map that moves by 1 pixel every third of a second to
  * run a mock version of flappy bird
  *
  * @author Nikhil Vishnoi
  * @since 06/13/21
  */
public class FlappyBirdPanel extends JPanel implements KeyListener{
  /** Fields */
  private Map m;
  private int move;
  private Image background;
  private Image bird;
  private Bird player;
  private int score;
  private boolean running,pressedEnter;
  public static int highScore;
  private Image top,bot,regB,regT;

  /** Constructor */
  public FlappyBirdPanel() {
    addKeyListener(this);
    m = new Map(GameConstants.width/GameConstants.factor,GameConstants.height/GameConstants.factor);
    move = 0;
    background = new ImageIcon("images/background.png").getImage();
    player = new Bird();
    bird = new ImageIcon("images/flapper.png").getImage();
    top = new ImageIcon("images/topOpening.png").getImage();
    bot = new ImageIcon("images/botOpening.png").getImage();
    regB = new ImageIcon("images/pipeB.png").getImage();
    regT = new ImageIcon("images/pipeT.png").getImage();
    running = true;
    pressedEnter = false;
    score = 0;
    highScore = recoverHighScore();
  }

  /** Overrides paintComponent */
  public void paintComponent(Graphics g) {
    g.drawImage(background, 0,0,GameConstants.width,GameConstants.height,null);
    g.setColor(Color.GREEN);
    drawMap(g);
    g.setColor(Color.BLACK);
    Font f = new Font("Serif",Font.BOLD,20);
    g.setFont(f);
    g.drawString("Score: "+score,GameConstants.width-125,20);
    g.drawString("High Score: "+highScore,GameConstants.width-170,40);

    drawBird(g);
    requestFocus();
    if(!running) {
      Font f2 = new Font("Serif",Font.BOLD,30);
      g.setFont(f2);
      g.drawString("GAME OVER(Space to Restart) ",GameConstants.width/2-GameConstants.width/3,GameConstants.height/2);
      g.setFont(f);
    }
  }


  /**
    * Uses Graphics to draw bird
    */
  public void drawBird(Graphics g) {
    g.drawImage(bird,player.getX(),player.getY(), GameConstants.factor,GameConstants.factor, null);
  }

  /**
    * Uses PaintComponent g and Map to create obsticles
    * @param Graphics g   Used to draw rectangles
  */
  public void drawMap(Graphics g) {
    int[][] pillars = m.getMap();
    int mult = GameConstants.factor;
    for(int r = 0; r<pillars.length;r++)
      for(int c = 0; c<pillars[r].length; c++)
        if(pillars[r][c] == 1) {
          if(m.isTopHole(r,c))
            g.drawImage(top,(c+1)*mult-move,r*mult,mult,mult,null);
          else if(m.isBotHole(r,c) )
            g.drawImage(bot,(c+1)*mult-move,r*mult,mult,mult,null);
          else if(m.isTopPipe(r,c) )
            g.drawImage(regT,(c+1)*mult-move,r*mult,mult,mult,null);
          else
            g.drawImage(regB,(c+1)*mult-move,r*mult,mult,mult,null);
        }
  }
  /*************************** Logic Methods ***********************************/

  /**
    * Uses move var and Bird object to deduce if the midpoint is in a pillar
    * @return true    if the bird's midpoint is in the pillar
    */
  public boolean isColiding() {
    if(player.getY() > GameConstants.height) return true;
    int[][] pillars = m.getMap();
    int mult = GameConstants.factor;
    for(int r = 0; r<pillars.length; r++) {
      for(int c = 0; c<3 && c<pillars[r].length; c++) {
        if(pillars[r][c] == 1) {
          Cord pTopLeft = new Cord((c+1)*mult-move,r*mult);
          Cord pBotRight = new Cord(pTopLeft.getX()+mult,pTopLeft.getY()+mult);
          Cord obj = new Cord(player.getX()+GameConstants.factor/2,player.getY()+GameConstants.factor/2);
          if(obj.isInBetween(pTopLeft,pBotRight)) { return true; }
        }
      }
    }
    return false;
  }
  /** Makes the bird drop by the grav Mult */
  public void gravity() {
    player.fall();
  }
  /** Ends the game */
  public void endGame() {
    running = false;
    if(score > highScore) {
      saveNewScore();
      highScore = score;
    }
  }
  /**
    * Restarts the game
    */
  public void restartGame() {
    m = new Map(GameConstants.width/GameConstants.factor,GameConstants.height/GameConstants.factor);
    move = 0;
    int i = GameConstants.height/2;
    player.setY(i);
    running = true;
    pressedEnter = false;
    score = 0;
  }
  /**
    * Moves Pillars to left
    */
  public void increment(){
    move++;
    if(move == GameConstants.factor) {
      m.moveLeft();
      if(m.isPillar(0)) score++;
      move = 0;
    }
  }

  /*********************** High Score Methods *********************************/
  /** Saves a new high score in a txt file */
  public void saveNewScore() {
    File txt = new File("hs.txt");
    PrintWriter write = null;
    write = FileUtils.openToWrite("hs.txt");
    write.println(score);
    write.close();
  }
  /** Checks for the existance of a saved highscore for the game and if it exists
    * it recovers that highscore
    * @return       int of highscore of the game
    */
  public int recoverHighScore() {
    File txt = new File("hs.txt");
    Scanner read = null;
    String s = "0";
    read = FileUtils.openToRead("hs.txt");
    if(read.hasNext()) s = read.nextLine();

    int retVal = 0;
    try {
      retVal = Integer.parseInt(s);
    }catch(NumberFormatException e){
      System.out.println("ERROR IN RECOVERING HIGHSCORE");
    }
    read.close();
    return retVal;
  }
  /**
    * Decides if the user entered enter to restart game
    */
  public boolean shouldRestart() {
    return pressedEnter;
  }

  /*************************** KeyListener Methods ****************************/
  public void keyTyped(KeyEvent e) {

  }
  public void keyReleased(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    if(running && e.getKeyChar() == ' ') player.jump();
    if(!running && e.getKeyChar() == ' ') pressedEnter = true;
    else pressedEnter = false;
  }
}
