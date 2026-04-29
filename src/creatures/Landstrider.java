package creatures;

import java.awt.Color;

import definitions.Creature;

public class Landstrider extends Creature {

    public Landstrider(int x, int y, int size) {
        super(x, y, 10, "Landstrider", "Plants", Color.GRAY);
        
    }

    public Creature reproduce() {
        if (age > 5 && health > 5) {
            return new Landstrider(x, y, 15);
        }
        return null;
    }
    
}
