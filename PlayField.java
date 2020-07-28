import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PlayField extends JPanel implements ActionListener {
    private Timer timer;
    private boolean status = false;
    private String game_status = "              Начните игру!";
    private Image background = new ImageIcon("textures/bracket.png").getImage();
    private Image opened_empty = new ImageIcon("textures/opened_empty.png").getImage();
    private Image opened_bomb = new ImageIcon("textures/opened_bomb.png").getImage();
    private Image[] images = new Image[]{
            new ImageIcon("textures/number_brick_1.png").getImage(),
            new ImageIcon("textures/number_brick_2.png").getImage(),
            new ImageIcon("textures/number_brick_3.png").getImage(),
            new ImageIcon("textures/number_brick_4.png").getImage(),
            new ImageIcon("textures/number_brick_5.png").getImage(),
            new ImageIcon("textures/number_brick_6.png").getImage(),
            new ImageIcon("textures/number_brick_7.png").getImage(),
            new ImageIcon("textures/number_brick_8.png").getImage()
    };
    private brick[][] bracket;
    private int dx;
    private int dy;
    private int max_bomb;
    private int bound_bombs = 30; // шанс создания мобмы = 100/11


    public PlayField() {
        setBackground(Color.white);
        addMouseListener(new FieldKeyListener());
        setFocusable(true);
        timer = new javax.swing.Timer(40, this);
        timer.start();
    }

    public void start() {
        bracket = new brick[10][10];
        max_bomb = 5;
        dx = opened_bomb.getWidth(null);
        dy = opened_bomb.getHeight(null);
        status = true;
        game_status = "";
        int pos_x = 0;
        int pos_y = 0;
        Random rnd = new Random();

        for (int i = 0; i < bracket.length; i++) {
            for (int j = 0; j < bracket.length; j++) {
                if (max_bomb > 0 && rnd.nextInt(100) < bound_bombs) {
                    max_bomb--;
                    bracket[i][j] = new bomb_brick(pos_x, pos_y, opened_bomb);
                } else {
                    bracket[i][j] = new empty_brick(pos_x, pos_y, opened_empty);
                }
                pos_x += opened_bomb.getWidth(null) + 1;

            }
            pos_x = 0;
            pos_y += opened_bomb.getHeight(null) + 1;
        }
        makeNum();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!status) {
            g.drawImage(background, 0, 0, null);
        } else {
            for (int i = 0; i < bracket.length; i++) {
                for (int j = 0; j < bracket.length; j++) {
                    g.drawImage(bracket[i][j].getCurrent_brick(), bracket[i][j].getPos_x(), bracket[i][j].getPos_y(), null);
                }
            }
        }
        g.drawString(game_status, Match.width / 2 - 100, Match.height / 2 - 10);
    }

    private void testCollide(Point p, boolean flag) {
        for (int i = 0; i < bracket.length; i++) {
            for (int j = 0; j < bracket.length; j++) {
                if (!bracket[i][j].isOpen()) { // Проверка, открыт или нет
                    if (p.x >= bracket[i][j].getPos_x() + Match.location_x && p.x <= bracket[i][j].getPos_x() + dx + Match.location_x &&
                            p.y - dy >= bracket[i][j].getPos_y() && p.y - dy <= bracket[i][j].getPos_y() + dy) {
                        if (flag) {
                            bracket[i][j].setFlaged_brick();
                        } else {
                            if (bracket[i][j].isBoom()) {
                                explodeAllBombs();
                                game_status = "Вы проиграли";
                            } else {
                                bracket[i][j].setOpened_brick();
                                openEmptyBricks(i, j);
                            }
                        }
                        repaint();
                        return;
                    }
                }
            }
        }

    }

    private void explodeAllBombs() {
        for (int k = 0; k < bracket.length; k++) {
            for (int l = 0; l < bracket.length; l++) {
                if (bracket[k][l].isBoom()) {
                    bracket[k][l].setOpened_brick();
                }
            }
        }
    }

    private void makeNum() {
        for (int k = 0; k < bracket.length; k++) {
            for (int l = 0; l < bracket.length; l++) {
                int count = 0;
                if (!bracket[k][l].isBoom()) {
                    if (k != 0 && l != 0) {
                        if (bracket[k - 1][l - 1].isBoom()) {
                            count++;
                        }
                    }
                    if (k != 0) {
                        if (bracket[k - 1][l].isBoom()) {
                            count++;
                        }
                    }
                    if (k != 0 && l != bracket.length - 1) {
                        if (bracket[k - 1][l + 1].isBoom()) {
                            count++;
                        }
                    }
                    if (l != 0) {
                        if (bracket[k][l - 1].isBoom()) {
                            count++;
                        }
                    }
                    if (l != bracket.length - 1) {
                        if (bracket[k][l + 1].isBoom()) {
                            count++;
                        }
                    }
                    if (k != bracket.length - 1 && l != 0) {
                        if (bracket[k + 1][l - 1].isBoom()) {
                            count++;
                        }
                    }
                    if (k != bracket.length - 1) {
                        if (bracket[k + 1][l].isBoom()) {
                            count++;
                        }
                    }
                    if (k != bracket.length - 1 && l != bracket.length - 1) {
                        if (bracket[k + 1][l + 1].isBoom()) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        bracket[k][l].isNum();
                        bracket[k][l].setNumberOnBrick(images[count-1]);
                    }
                }
            }
        }
    }

    private void openEmptyBricks(int i, int j) {
        try {
            if (!bracket[i - 1][j].isBoom() && !bracket[i - 1][j].isOpen() && !bracket[i - 1][j].getIsNum()) {
                bracket[i - 1][j].setOpened_brick();
                openEmptyBricks(i - 1, j);
            }
        } catch (Exception e) {
        }
        try {
            if (!bracket[i + 1][j].isBoom() && !bracket[i + 1][j].isOpen() && !bracket[i + 1][j].getIsNum()) {
                bracket[i + 1][j].setOpened_brick();
                openEmptyBricks(i + 1, j);
            }
        } catch (Exception e) {
        }
        try {
            if (!bracket[i][j - 1].isBoom() && !bracket[i][j - 1].isOpen() && !bracket[i][j - 1].getIsNum()) {
                bracket[i][j - 1].setOpened_brick();
                openEmptyBricks(i, j - 1);
            }
        } catch (Exception e) {
        }
        try {
            if (!bracket[i][j + 1].isBoom() && !bracket[i][j + 1].isOpen() && !bracket[i][j + 1].getIsNum()) {
                bracket[i][j + 1].setOpened_brick();
                openEmptyBricks(i, j + 1);
            }
        } catch (
                Exception e) {
        }
    }

    public void checkResult() {
        for (int i = 0; i < bracket.length; i++) {
            for (int j = 0; j < bracket.length; j++) {
                if (bracket[i][j].isBoom() && bracket[i][j].isFlaged()) {
                    continue;
                } else {
                    if (bracket[i][j].isBoom() && !bracket[i][j].isFlaged()) {
                        explodeAllBombs();
                        game_status = "Неправельно! Вы проиграли";
                        repaint();
                        return;
                    }
                }
            }
        }
        explodeAllBombs();
        game_status = "Вы выйграли! Поздравляем !";
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    class FieldKeyListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (game_status == "") {
                super.mouseClicked(e);
                Point p = e.getLocationOnScreen();
                if (e.getButton() == 1) {
                    testCollide(p, false);
                }
                if (e.getButton() == 3) {
                    testCollide(p, true);
                }
            }
        }
    }
}
