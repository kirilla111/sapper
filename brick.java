import javax.swing.*;
import java.awt.*;

public class brick {
    private Image closed_brick = new ImageIcon("textures/closed_brick.png").getImage();
    private Image flaged_brick = new ImageIcon("textures/flaged_bomb.png").getImage();
    private Image opened_brick;
    private Image current_brick;
    private int pos_x;
    private int pos_y;
    private boolean boom;
    private boolean isOpen = false;
    private boolean isNum = false;

    public brick(int pos_x, int pos_y, Image opened_brick, boolean boom) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.current_brick = closed_brick;
        this.opened_brick = opened_brick;
        this.boom = boom;
    }

    public void isNum() {
        isNum = true;
    }
    public boolean getIsNum() {
        return isNum;
    }
    public Image getCurrent_brick() {
        return current_brick;
    }

    public void setOpened_brick() {
        this.current_brick = this.opened_brick;
        isOpen = true;
    }

    public void setFlaged_brick() {
        this.current_brick = this.flaged_brick;
    }

    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public boolean isBoom() {
        return boom;
    }

    public boolean isFlaged() {
        if (current_brick == flaged_brick) return boom;
        else return false;
    }


    public boolean isOpen() {
        return isOpen;
    }
    public void setNumberOnBrick(Image number){
        this.opened_brick = number;
    }
}
