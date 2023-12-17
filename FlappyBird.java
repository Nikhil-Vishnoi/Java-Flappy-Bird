import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
  * Utilizes the Timer class and JFrame to create a working JPanel that runs a
  * game like flappy bird
  *
  * @author Nikhil Vishnoi
  * @since 06/13/21
  */
public class FlappyBird {
  private JFrame window;
  private FlappyBirdPanel gamePanel;
  private Timer tGrav,tMap,logic;
  private boolean running;
  /** Constructor */
  public FlappyBird(){

    window = new JFrame("Flappy Bird");
    window.setSize(GameConstants.width,GameConstants.height);
    window.setLocation(0,0);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
    window.setResizable(true);
    window.requestFocus();
    //Holds all the Jpanels for each level
    gamePanel = new FlappyBirdPanel();
    //Adds the JPanel to the ContentPlane
    window.getContentPane().add(gamePanel, BorderLayout.CENTER);
    running = true;
  }

  /** Main Method */
  public static void main(String[] args) {
    FlappyBird obj = new FlappyBird();
    obj.run();
  }
  /** Action Listener that moves the map to the left */
  class timer implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      SwingUtilities.updateComponentTreeUI(window);
      gamePanel.repaint();
      gamePanel.increment();
    }
  }
  /** Action listener that moves the bird down */
  class grav implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      gamePanel.gravity();
    }
  }
  /** Action Listener used to check if the game is over */
  class repeater implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      gamePanel.repaint();
      if(running && gamePanel.isColiding()) {
        gamePanel.endGame();
        stopTimers();
        running = false;
      }
      if(!running && gamePanel.shouldRestart()) {
        running = true;
        gamePanel.restartGame();
        startTimers();
      }
    }
  }
  /** Initializes timers to run the game */
  public void run() {
    int countdown = 1000/GameConstants.fps;
    timer motion = new timer();
    tMap = new Timer(countdown,motion);
    tMap.start();

    grav bird = new grav();
    tGrav = new Timer(GameConstants.gravMult, bird);
    tGrav.start();

    int milli = 1;
    repeater rep = new repeater();
    logic = new Timer(milli,rep);
    logic.start();

    SwingUtilities.updateComponentTreeUI(window);


  }
  /** Stops all the timers from running */
  public void stopTimers() {
    tMap.stop();
    tGrav.stop();
  }
  /** Starts all the timers */
  public void startTimers() {
    tMap.start();
    tGrav.start();
  }
}
