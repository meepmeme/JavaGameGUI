import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {
    // private vars here
    private JTable tbl1;
    private JLabel gameInfo;
    private JLabel label1;
    private JTextField tf1;
    private JButton btn1;
    private JButton btn2;
    private int[][] bombs;;

    public Main() {
        super("JMinesweeper");
        bombs = new int[2][11]; // 2 coords per bomb, 11 bombs
        this.setSize(500, 500);
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        String[][] vals = new String[11][11];
        // outer rows and cols will have coordinate guides in form |n|
        // allows me to have full minesweeper name but use single-digit coords
        String[] colTitles = { "M", "I", "N", "E", "S", "W", "E", "E", "P", "E", "R" };
        tbl1 = new JTable(vals, colTitles);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 5;
        this.add(tbl1, c);

        gameInfo = new JLabel("Total bombs: 0, Bombs flagged: 0");
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
        btn2 = new JButton("Flag");

        Handler hand = new Handler();
        // add things to handler here
        tf1.addActionListener(hand);

        this.pack();
        this.setVisible(true);
    }

    private class Handler implements ActionListener {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
