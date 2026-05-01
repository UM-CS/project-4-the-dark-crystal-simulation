package definitions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Creature {

    public int x, y;
    private int vx = 0, vy = 0;
    public int health;
    public int age;
    public String name;
    public String diet;
    public Color color;
    private double deathChance = 0.001;

    public Creature(int x, int y, int health, String name, String diet, Color color) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.name = name;
        this.diet = diet;
        this.age = 0;
        this.color = color;
    }

    public void lifeCycle() {
        move();
        
        if (Math.random() < 0.1) {
            age++;
            health--;
        }
    }
    
    public void move() {
        if (Math.random() < .2) {
            if (Math.random() < 0.10 || (vx == 0 && vy == 0)) {
                vx += (int)(Math.random() * 3) - 1;
                vy += (int)(Math.random() * 3) - 1;
            }

            x += vx;
            y += vy;

            x = Math.max(0, Math.min(x, 49));
            y = Math.max(0, Math.min(y, 49));
        }
    }

    public boolean die() {
        if (health <= 0 || age > 10) {
            System.out.println(name + " has died.");
            return true;
        }
        return false;
    }

    public boolean randomDeath(){
        if(Math.random() < deathChance){
            System.out.println(name + " died randomly.");
            return true;
        }
        return false;
    }
    

    public Creature reproduce() {
        if (age > 5 && health > 5) {
            System.out.println(name + " has reproduced.");
            return new Creature(x, y, health, name, diet, color);
        }
        return null;
    }

    public void eat(String diet) {
        if (this.diet.equals(diet)) {
            health += 5;
            System.out.println(name + " has eaten " + diet + " and gained health.");
        } else {
            System.out.println(name + " cannot eat " + diet + ".");
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        int drawX = x * 15 + 4;
        int drawY = y * 15 + 4;
        int drawSize = 15 - 8;
        g.fillRoundRect(drawX, drawY, drawSize, drawSize, 10, 10);
        g.setFont(new Font("SansSerif", Font.PLAIN, 9));
        g.setColor(Color.WHITE); 
        g.drawString(name, drawX + 2, drawY + (drawSize / 2) + 5);
    }
    
}
