package JMinesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main extends JFrame {
  // instantiate objects
  private JTable tbl1;
  private JLabel gameInfo;
  private JLabel label1;
  private JTextField tf1;
  private JButton btn1;
  private JButton btn2;
  private DefaultTableModel model;
  private JButton btn3;

  // creates UI, taking the Minesweeper object from the start as an argument.
  public Main(Minesweeper s) {
    super("JMinesweeper");
    this.setSize(500, 500);
    this.getContentPane().setBackground(Color.green);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    model = new DefaultTableModel(s.vals, s.colTitles());
    tbl1 = new JTable(model);
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 5;
    c.gridheight = 5;
    this.add(tbl1, c);

    // helpful game info.
    gameInfo = new JLabel(
        "Minesweeper clone. \"CLR\" is clear, \"F\" is flagged, and any number is how many bombs are near.");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 5;
    c.gridheight = 1;
    this.add(gameInfo, c);

    // coordinate display
    label1 = new JLabel("[X,Y]: [?,?]");
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(label1, c);

    // coordinate input
    // requires that you press enter to submit new coords
    tf1 = new JTextField(3); // "X,Y". will be read as XCoord, *, YCoord
    c.gridx = 1;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(tf1, c);

    // the click button. explodes bombs.
    btn1 = new JButton("Select");
    c.gridx = 2;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(btn1, c);

    // the less-explodey button.
    btn2 = new JButton("Flag");
    c.gridx = 3;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(btn2, c);

    // resets game.
    btn3 = new JButton("Restart");
    c.gridx = 4;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(btn3, c);

    // gives buttons and things the ability to trigger stuff
    Handler hand = new Handler(s, model);
    tf1.addActionListener(hand);
    btn1.addActionListener(hand);
    btn2.addActionListener(hand);
    btn3.addActionListener(hand);

    // make the ui?
    this.pack();
    this.setVisible(true);
  }

  // the thing that runs when you press buttons.
  private class Handler implements ActionListener {
    // data to be stored inbetween actions, but not relevant to UI creation.
    private int clickX;
    private int clickY;
    private String tmp;
    private Minesweeper s;
    private DefaultTableModel f;

    // the handler itself, with arguments to pass along access to needed
    // vars/objects.
    public Handler(Minesweeper seep, DefaultTableModel tm) {
      super();
      s = seep;
      f = tm;
    }

    // runs when something happens
    public void actionPerformed(ActionEvent event) {
      // if is restart game button, restart game.
      if (event.getSource() == btn3) {
        JOptionPane.showMessageDialog(null, "Restarting game.");
        s.restartGame();
        f.setDataVector(s.vals, s.colTitles());
      }
      // if textBox, update coordinates.
      if (event.getSource() == tf1) {
        tmp = event.getActionCommand();
        clickX = getInt(tmp.substring(0, 1));
        clickY = getInt(tmp.substring(2, 3));
        label1.setText("[X,Y]: [" + clickX + "," + clickY + "]");
        JOptionPane.showMessageDialog(null, "set cursor coords to " + clickX +
                                                "," + clickY);
      }
      // click a spot. passes false to indicate click.
      if (event.getSource() == btn1) {
        JOptionPane.showMessageDialog(null, s.clickSpot(clickY, clickX, false));
        f.setDataVector(s.vals, s.colTitles());
      }
      // flag a spot. passes true to indicate is a flag.
      if (event.getSource() == btn2) {
        JOptionPane.showMessageDialog(null, s.clickSpot(clickY, clickX, true));
        f.setDataVector(s.vals, s.colTitles());
      }
    }
  }

  // create minesweeper object and pass it to the UI.
  public static void main(String[] args) {
    int numBombs = 21; // default

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--help")) {
        System.out.println("JMinesweeper: a basic minesweeper clone");
        System.out.println("Commandline arguments:");
        System.out.println("-b N: generate N bombs, provided N>0");
        System.out.println("--help: display this help menu");
        System.exit(0);
      } else if (args[i].equals("-b") && args.length > i + 1) {
        // bomb count can be passed as an argument!
        try {
          numBombs = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException ex) {
          ex.printStackTrace();
        }
        if (numBombs < 1)
          numBombs = 1; // minimum 1 bomb SOMEWHERE.
      }
    }

    Minesweeper sweep = new Minesweeper(numBombs);
    new Main(sweep);
  }

  // convert string to int. breaks when non-int happens.
  public int getInt(String str) {
    int number = 0;
    try {
      number = Integer.parseInt(str);
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
    }
    return number;
  }
}
