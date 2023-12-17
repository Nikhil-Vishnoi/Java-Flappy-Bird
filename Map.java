/**
  * Uses Algorithms to create a 2D array that symbolizes the map that the bird
  * needs to go through while making it never ending and always possible to jump.
  *
  * @author Nikhil Vishnoi
  * @since 06/13/21
  */
public class Map {
  private int[][] map;
  private int distance;
  /** Constructor */
  public Map(int row, int col) {
    distance = 0;
    map = new int[row][col];
    for(int r = 0; r<row; r++)
      for(int c = 0; c<col; c++)
        map[r][c] = 0;
    createMap();
  }

  /** Returns the map */
  public int[][] getMap() { return map; }

  /**
    * Initializes the map
    * 1's represent pillars
    * @precondition map[][] is initialized already with 0s in each index
    */
  public void createMap() {
    for(int c = 3; c<map[0].length; c = c+3+(int)(Math.random()*3)) {
      if(shouldPutPillar(c)) putPillar(c);
    }
  }
  /**
    * Moves everything in the array to the left
    */
  public void moveLeft() {
    for(int r = 0; r<map.length; r++) {
      for(int c = 0; c<map[0].length; c++) {
        if(c+1 < map[0].length) map[r][c] = map[r][c+1];
        else map[r][c] = 0;
      }
    }
    if(shouldPutPillar(map[0].length-1)) putPillar(map[0].length-1);
    //System.out.println();
  //  printMap();
  }
  /**
    * Checks if there should be a pillar in the row
    * IF there are no pillars right before and after it returns true
    * ELSE returns false
    *
    * @param row to check if there should be a pillar there.
    */
  public boolean shouldPutPillar(int c) {
    int var = (int)(Math.random()*3)+3;
    for(int col = c-var; col < map[0].length && col < c+var; col++){
      for(int r = 0; r<map.length; r++)
          if((col > 0 && col < map[0].length) && ( r > 0 && r < map.length ) && map[r][col] == 1)
            return false;
    }
    return true;
  }
  /**
    * Places a Piller with a 5 row hole that is adjacent to the last 5 row hole
    * of the previous column making the jump possible.
    *
    * @param Row to put the pillar in
    * @precondition  Pillar should belong there
    */
  public void putPillar(int c){
    int gapStart = (int)(Math.random()*(map[0].length-1));
    for(int r = 0; r<map.length;r++) {
      if(r != gapStart && r+1 != gapStart) map[r][c] = 1;
    }
  }
  /**
    * Checks if there is a pillar at a index
    * @param      Column to check
    * @return     boolean true if pillar at col c
    */
  public boolean isPillar(int c) {
    for(int r =0; r<map.length; r++) {
      if(map[r][c] == 1) return true;
    }
    return false;
  }
  /**
    * Checks if coord is the bot of a pipe
    * @precondition there is a pipe at the cullomn
    * @param    r to check
    * @param    c to check
    * @return   boolean representing if coord is the bot of a hole
    */
  public boolean isBotHole(int r, int c) {
    if(r-1 < 0) return false;
    if(map[r-1][c] != 1) return true;
    return false;
  }
  /**
    * Checks if coord is the top of a pipe
    * @precondition there is a pipe at the culomn
    * @param    r to check
    * @param    c to check
    * @return   boolean representing if coord is the top of a hole
    */
  public boolean isTopHole(int r, int c) {
    if(r+1 >= map.length) return false;
    if(map[r+1][c] != 1) return true;
    return false;
  }
  /**
    * Checks if the coord is a pipe above the opening hole
    * @precondition there is a pipe at inputed column
    * @param   r to check
    * @param   c to check
    * @return  boolean representing if coord is above the hole
    */
  public boolean isTopPipe(int r, int c) {
    for(int row = 0; row<r; row++) {
      if(map[row][c] != 1) return false;
    }
    return true;
  }
  /** Used to test the algorithm */
  public void printMap() {

    for(int r = 0; r<map.length; r++) {
      for(int c = 0; c<map[0].length; c++) {
        System.out.print(map[r][c]+" ");
      }
      System.out.println();
    }

  }

}
