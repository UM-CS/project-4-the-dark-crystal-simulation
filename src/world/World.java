package world;
import java.util.ArrayList;
import java.util.Random;

import creatures.Fizzgig;
import creatures.Landstrider;
import creatures.Nurloc;
import definitions.Creature;
import definitions.Food;
import definitions.Terrain;

public class World {

    ArrayList<Creature> creatures = new ArrayList<>();
    ArrayList<Food> food = new ArrayList<>();
    Random r = new Random();

    int width = 100;
    int height = 100;

    Terrain[][] map = new Terrain[width][height];

    public void updateMap() {

    }



    public void createCreature() {
        int initialSpawn = 5;
        
        for (int i = 0; i < initialSpawn; i++) {
            creatures.add(new Landstrider(r.nextInt(width), r.nextInt(height)));
            creatures.add(new Fizzgig(r.nextInt(width), r.nextInt(height)));
            creatures.add(new Nurloc(r.nextInt(width), r.nextInt(height)));
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
            } else {
                Creature baby = c.reproduce();
                if (baby != null) {
                    creatures.add(baby);
                }
            }
        }
    }

    public static void main(String[] args) {
        World world = new World();
        world.createCreature();

        for (int i = 0; i < 100; i++) {
            world.roll();
        }
    }
}
