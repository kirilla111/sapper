import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Match extends JFrame {
    public static final int width = 525;
    public static final int height = 568;
    public static final int location_x = 550;
    public static final int location_y = 0;


    public Match() {
        setTitle("FakeBoom");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width,height);
        setLocation(location_x,location_y);
        setVisible(true);
        PlayField pf = new PlayField();
        add(pf);
        MenuBar mbar = new MenuBar();
        setMenuBar(mbar);

        Menu file = new Menu("Game");
        MenuItem i1, i2,i3;
        file.add(i1 = new MenuItem("Start new game"));
        file.add(i2 = new MenuItem("Complete!"));
        file.add(i3 = new MenuItem("Exit"));

        mbar.add(file);

        i1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {pf.start();
            }
        });
        i2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pf.checkResult();
            }
        });
        i3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
    }

    public static void main(String[] args) {
        Match m = new Match();
    }

}
