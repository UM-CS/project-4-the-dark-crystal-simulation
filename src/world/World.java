package world;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.swing.*;

import creatures.Fizzgig;
import creatures.Landstrider;
import creatures.Nurloc;
import definitions.Creature;
import definitions.Food;
import definitions.Terrain;
import definitions.die;
import definitions.eat;
import definitions.reproduce;
import definitions.move;

public class World extends JPanel{
    private final int GRID_COUNT = 50;
    private final int CELL_SIZE = 15;
    private Terrain[][] map = new Terrain[GRID_COUNT][GRID_COUNT];
    private Color plantColor = new Color(29,168,57);

    ArrayList<Creature> creatures = new ArrayList<>();
    ArrayList<Food> food = new ArrayList<>();
    Random r = new Random();

    int width = 100;
    int height = 100;

    public World(int n) {
        setBackground(new Color(20, 20, 30));
        generateMap();
        
        // Registry of types
        Random r = new Random();
        List<Supplier<Creature>> types = Arrays.asList(
            () -> new Fizzgig(r.nextInt(GRID_COUNT), r.nextInt(GRID_COUNT), CELL_SIZE),
            () -> new Landstrider(r.nextInt(GRID_COUNT), r.nextInt(GRID_COUNT), CELL_SIZE),
            () -> new Nurloc(r.nextInt(GRID_COUNT), r.nextInt(GRID_COUNT), CELL_SIZE)
            
        );

        creatures = new ArrayList<>();
        for(int i=0; i<n; i++) creatures.add(types.get(r.nextInt(types.size())).get());

        new Timer(100, e -> {

            roll(); 
            eatFood();
    
            repaint();
        }).start();
        
    }
         

    private void generateMap() {
        for (int x = 0; x < GRID_COUNT; x++) {
            for (int y = 0; y < GRID_COUNT; y++) {
                map[x][y] = Terrain.GROUND;
                if (x < 15 && y < 15) {
                map[x][y] = Terrain.FOREST;
                } else if (x > 35 && y > 35) {
                map[x][y] = Terrain.SAND;
                } else if (x > 20 && x < 25) {
                map[x][y] = Terrain.RIVER;
                }
            }
        }
    }

    public void createCreature() {
        int initialSpawn = 5;
        
        for (int i = 0; i < initialSpawn; i++) {
            creatures.add(new Landstrider(r.nextInt(GRID_COUNT), r.nextInt(GRID_COUNT), 15));
            creatures.add(new Fizzgig(r.nextInt(GRID_COUNT), r.nextInt(GRID_COUNT), 15));
            creatures.add(new Nurloc(r.nextInt(GRID_COUNT), r.nextInt(GRID_COUNT), 15));
        }
    }

    
    public void spawnFood() {
        int x = r.nextInt(GRID_COUNT);
        int y = r.nextInt(GRID_COUNT);

        if (r.nextBoolean()) {
            food.add(new Food(x, y, "Plants", plantColor));
        } else {
            food.add(new Food(x, y, "Bugs", Color.CYAN));
        }

    }

    public void eatFood() {
        for (Creature c : creatures) {
            for (Food f : food) {
                if (c.x == f.x && c.y == f.y) {
                    c.eat(f.type);
                    food.remove(f);
                    break;
                }
            }
        }
    }


    public void roll() {
        if (r.nextInt(100) < 10) {
            createCreature();
        }

        if (r.nextInt(100) < 20) {
            spawnFood();
        }

        for (int i = 0; i < creatures.size(); i++) {
            Creature c = creatures.get(i);
            c.lifeCycle();
            if (c.die()) {
                creatures.remove(i--);
            } 
            else if(c.randomDeath()){
                    creatures.remove(i--);
            }
            else{
                if (creatures.size() < 50) {
                    Creature baby = c.reproduce();
                    if (baby != null) {
                        creatures.add(baby);
                    }
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < GRID_COUNT; x++) {
            for (int y = 0; y < GRID_COUNT; y++) {
                if (map[x][y] == Terrain.RIVER) g.setColor(new Color(0, 100, 200));
                else if (map[x][y] == Terrain.FOREST) g.setColor(new Color(0, 200, 0));
                else if (map[x][y] == Terrain.SAND) g.setColor(new Color(200, 200, 0));
                else g.setColor(new Color(40, 40, 50));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(new Color(60, 60, 80));
                g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        for (Creature a : creatures) a.draw(g);
        for (Food b : food) b.draw(g);
    }

    public static void main(String[] args) {
    
        JFrame f = new JFrame("Dark Crystal World");
        f.add(new World(200));
        f.pack();
        f.setSize(765, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
}
}

