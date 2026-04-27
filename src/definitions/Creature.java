package definitions;

public class Creature {

    public int x, y;
    public int health;
    public int age;
    public String name;
    public String diet;

    public Creature(int x, int y, int health, String name, String diet) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.name = name;
        this.diet = diet;
        this.age = 0;
    }

    public void lifeCycle() {
        age++;
        health--;
        move();
    }
    
    public void move() {
        x += (int)(Math.random() * 3) - 1;
        y += (int)(Math.random() * 3) - 1; 
    }

    public boolean die() {
        if (health <= 0 || age > 10) {
        System.out.println(name + " has died.");
        return true;
        }
        return false;
    }

    public Creature reproduce() {
        if (age > 5 && health > 5) {
            return new Creature(x, y, health, name, diet);
        }
        return null;
    }

    public void eat(String diet) {
        if (this.diet.equals(diet)) {
            health += 5;
            System.out.println(name + " has eaten " + diet + " and gained health.");
        }
    }
    
}
