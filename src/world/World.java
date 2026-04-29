package world;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.swing.*;

import javax.swing.Timer;

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
            () -> new Fizzgig(r.nextInt(5), r.nextInt(GRID_COUNT), CELL_SIZE),
            () -> new Landstrider(r.nextInt(5), r.nextInt(GRID_COUNT), CELL_SIZE),
            () -> new Nurloc(r.nextInt(5), r.nextInt(GRID_COUNT), CELL_SIZE)
            
        );

        creatures = new ArrayList<>();
        for(int i=0; i<n; i++) creatures.add(types.get(r.nextInt(types.size())).get());

        new Timer(200, e -> {
            for (Creature creature : creatures){
                if (creature instanceof reproduce) {
                    ((reproduce) creature).reproduce(map);
                }
                if (creature instanceof die) {
                    ((die) creature).die(map);
                }
                if (creature instanceof eat) {
                    ((eat) creature).eat(map);
                }
                if(creature instanceof move) {
                    ((move) creature).move(map);
                }
            }
            repaint();
        }).start();
    }
         

    private void generateMap() {
        for (int x = 0; x < GRID_COUNT; x++) {
            for (int y = 0; y < GRID_COUNT; y++) {
                // Create a normal river (columns 9 and 10)
                if (x == 9 || x == 10) map[x][y] = Terrain.RIVER;
                // Create a void river (columns 13 and 14)
                else if (x == 13 || x == 14) map[x][y] = Terrain.FOREST;
                else if (x == 20 || x == 21) map[x][y] = Terrain.SAND;
                else map[x][y] = Terrain.GROUND;
            }

            
        }

    }
    



    
    public void updateMap() {
    }



    public void createCreature() {
        int initialSpawn = 5;
        
        for (int i = 0; i < initialSpawn; i++) {
            creatures.add(new Landstrider(r.nextInt(width), r.nextInt(height), 15));
            creatures.add(new Fizzgig(r.nextInt(width), r.nextInt(height), 15));
            creatures.add(new Nurloc(r.nextInt(width), r.nextInt(height), 15));
        }
    }

    
    public void spawnFood() {
        int x = r.nextInt(100);
        int y = r.nextInt(100);

        if (r.nextBoolean()) {
            food.add(new Food(x, y, "Plants"));
        } else {
            food.add(new Food(x, y, "Bugs"));
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
                creatures.remove(i);
                i--;
            }
            else if (c.randomDeath()){
                creatures.remove(i);
                i--;
            }
            else {
                Creature baby = c.reproduce();
                if (baby != null) {
                    creatures.add(baby);
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
    }

    public void worldTimer(){
        Timer timer = new Timer(1000, e -> {
            roll();
        });
        timer.start();

    }

    public static void main(String[] args) {
        // World world = new World();
        //world.createCreature();`
        JFrame f = new JFrame("World");
        f.add(new World(200));
        f.pack();
        f.setSize(765, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        //for (int i = 0; i < 100; i++) {
            //world.roll();
        //}
    }
}

