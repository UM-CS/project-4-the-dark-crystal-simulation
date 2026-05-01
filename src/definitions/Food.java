package definitions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Food {

    public int x, y;
    public String type;
    public Color color;

    public Food(int x, int y, String type, Color color) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
    }


    public void draw(Graphics g) {
        g.setColor(color);
        int drawX = x * 15 + 4;
        int drawY = y * 15 + 4;
        int drawSize = 15 - 5;
        g.fillRoundRect(drawX, drawY, drawSize, drawSize, 10, 10);
        g.setFont(new Font("SansSerif", Font.PLAIN, 9));
        g.setColor(Color.PINK); 
        g.drawString(type, drawX + 2, drawY + (drawSize / 2) + 5);
    }
}
