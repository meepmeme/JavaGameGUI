import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main extends JFrame {
  // private vars here
  private JTable tbl1;
  private JLabel gameInfo;
  private JLabel label1;
  private JTextField tf1;
  private JButton btn1;
  private JButton btn2;
  private DefaultTableModel model;

  public Main(Minesweeper s) {
    super("JMinesweeper");
    this.setSize(500, 500);
    this.getContentPane().setBackground(Color.white);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    // outer rows and cols will have coordinate guides in form |n|
    // allows me to have full minesweeper name but use single-digit coords
    model = new DefaultTableModel(s.vals, s.colTitles());
    tbl1 = new JTable(model);
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 5;
    c.gridheight = 5;
    this.add(tbl1, c);

    gameInfo = new JLabel(
        "Minesweeper clone. \"_\" is clear, \"f\" is flagged, and any number is how many bombs are near.");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 5;
    c.gridheight = 1;
    this.add(gameInfo, c);

    label1 = new JLabel("[X,Y]: [?,?]");
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 2;
    c.gridheight = 1;
    this.add(label1, c);

    tf1 = new JTextField(3); // "X,Y". will be read as XCoord, *, YCoord
    c.gridx = 2;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(tf1, c);

    btn1 = new JButton("Select");
    c.gridx = 3;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(btn1, c);

    btn2 = new JButton("Flag");
    c.gridx = 4;
    c.gridy = 6;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(btn2, c);

    Handler hand = new Handler(s, model);
    // add things to handler here
    tf1.addActionListener(hand);
    btn1.addActionListener(hand);
    btn2.addActionListener(hand);

    this.pack();
    this.setVisible(true);
  }

  private class Handler implements ActionListener {
    // private vars here
    private int clickX;
    private int clickY;
    private String tmp;
    private Minesweeper s;
    private DefaultTableModel f;

    public Handler(Minesweeper seep, DefaultTableModel tm) {
      super();
      s = seep;
      f = tm;
    }

    public void actionPerformed(ActionEvent event) {
      // do something
      if (event.getSource() == tf1) {
        tmp = event.getActionCommand();
        clickX = getInt(tmp.substring(0, 1));
        clickY = getInt(tmp.substring(2, 3));
        label1.setText("[X,Y]: [" + clickX + "," + clickY + "]");
        JOptionPane.showMessageDialog(null, "set cursor coords to " + clickX +
                                                "," + clickY);
      }
      if (event.getSource() == btn1) {
        JOptionPane.showMessageDialog(null, s.clickSpot(clickX, clickY, false));
        f.setDataVector(s.vals, s.colTitles());
      }
      if (event.getSource() == btn2) {
        JOptionPane.showMessageDialog(null, s.clickSpot(clickX, clickY, true));
        f.setDataVector(s.vals, s.colTitles());
      }
    }
  }

  public static void main(String[] args) {
    Minesweeper sweep = new Minesweeper();
    new Main(sweep);
  }

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
