package JMinesweeper;

import java.util.ArrayList;

// the actual minesweeper game logic.
public class Minesweeper {

  // data about current game
  private int[][] bombs; // coords of bombs
  public String[][] vals; // game grid
  private ArrayList<String> checked; // temporary var used when checking blank spaces.
  private int bombCount; // how many bombs?
  private int flagCount; // flags placed. used in scoring.
  private int clrPoints; // used to score later...
  private int flagPoints; // used to score later...
  private int falseFlagPoints; // used to score later...

  // create the minesweeper game.
  public Minesweeper(int b) {
    bombCount = b; // bomb count as an argument!
    flagCount = 0;
    clrPoints = 1;
    flagPoints = 15;
    falseFlagPoints = -10;
    bombs = new int[2][bombCount]; // 2 coords per bomb, bombCount bombs
    checked = new ArrayList<String>();
    generateBombs();
    vals = new String[11][11];
    for (int i = 0; i < vals.length; i++) {
      for (int j = 0; j < vals[0].length; j++) {
        vals[i][j] = "";
      }
    }
    for (int i = 1; i < 10; i++) {
      vals[0][i] = "\\" + i + "/";
      vals[10][i] = "/" + i + "\\";
      vals[i][0] = "|" + i + "|";
      vals[i][10] = "|" + i + "|";
    }
  }

  // a copy of the constructor, modified and trimmed to effectively restart the
  // game.
  public void restartGame() {
    flagCount = 0;
    generateBombs();
    for (int i = 0; i < vals.length; i++) {
      for (int j = 0; j < vals[0].length; j++) {
        vals[i][j] = "";
      }
    }
    for (int i = 1; i < 10; i++) {
      vals[0][i] = "\\" + i + "/";
      vals[10][i] = "/" + i + "\\";
      vals[i][0] = "|" + i + "|";
      vals[i][10] = "|" + i + "|";
    }
    checked.clear();
  }

  // getter for bombCount
  public int getBombNum() {
    return bombCount;
  }

  // returns array of letters that is SUPPOSED to be a game title!
  public String[] colTitles() {
    return new String[] { "M", "I", "N", "E", "S", "W", "E", "E", "P", "E", "R" };
  }

  // creates randomGen bombs, they occasionally overlap.
  public void generateBombs() {
    for (int i = 1; i < bombs[0].length - 1; i++) {
      bombs[0][i] = ((int) (Math.random() * 9)) + 1;
      bombs[1][i] = ((int) (Math.random() * 9)) + 1;
    }
  }

  // a sometimes-recursive method that takes a position and updates the game grid
  // based on what it finds
  private void updateGameGrid(int x, int y) {
    boolean isChecked = false;
    if (!(x == 0 || x == 10 || y == 0 || y == 10)) {
      if (!(isBomb(x, y))) {
        if (getBombCount(x, y) != 0) {
          vals[x][y] = (getBombCount(x, y) + "");
        } else {
          for (int i = 0; i < checked.size(); i++) {
            if (checked.get(i).equals((x + "," + y)))
              isChecked = true;
          }
          if (isChecked == false) {
            checked.add(x + "," + y);
            vals[x][y] = "CLR";
            updateGameGrid(x - 1, y);
            updateGameGrid(x, y - 1);
            updateGameGrid(x, y + 1);
            updateGameGrid(x + 1, y);
          }
          checked.add(x + "," + y);
        }
      }
    }
  }

  // returns how many bombs are around a given point.
  private int getBombCount(int x, int y) {
    int bmb = 0;
    for (int i = x - 1; i <= x + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        if (isBomb(i, j))
          bmb++;
      }
    }
    return bmb;
  }

  // returns a boolean for if a given point is a bomb.
  private boolean isBomb(int x, int y) {
    for (int i = 0; i < bombs[0].length; i++) {
      if (bombs[0][i] == x && bombs[1][i] == y) {
        return true;
      }
    }
    return false;
  }

  // takes coordinates and a boolean for whether this is a flag
  // and runs the right code to update things.
  // returns a string based on what it finds
  public String clickSpot(int x, int y, boolean flag) {
    checked.clear();
    if (isBomb(x, y)) {
      if (flag) {
        if (vals[x][y].equals("F")) {
          flagCount--;
          vals[x][y] = "";
          return ("Un-flagged " + y + "," + x);
        } else {
          flagCount++;
          vals[x][y] = "F";
          return ("Flagged " + y + "," + x);
        }
      } else {
        vals[x][y] = "X";
        return ("Clicked " + y + "," + x + ".\nIt was a bomb.\nScore: " + calculateScore());
      }
    } else {
      if (flag) {
        if (vals[x][y].equals("F")) {
          flagCount--;
          vals[x][y] = "";
          return ("Un-flagged " + y + "," + x);
        } else {
          flagCount++;
          vals[x][y] = "F";
          return ("Flagged " + y + "," + x);
        }
      } else {
        updateGameGrid(x, y);
        return ("Clicked " + y + "," + x + ".\nUpdated Game Grid.");
      }
    }
  }

  // end-of-game score calculator.
  // treats number spaces as clear.
  private int calculateScore() {
    int scr = 0;
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        if (vals[i][j].equals("F")) {
          if (isBomb(i, j)) {
            scr += flagPoints;
          } else {
            scr -= falseFlagPoints;
          }
        } else if (vals[i][j].equals("CLR")) {
          scr += clrPoints;
        } else if (vals[i][j].equals("")) {
          scr += 0;
        } else {
          scr += clrPoints;
        }
      }
    }
    return scr;
  }
}
