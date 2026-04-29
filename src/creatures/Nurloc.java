package creatures;

import java.awt.Color;

import definitions.Creature;

public class Nurloc extends Creature {
    
    public Nurloc(int x, int y, int size) {
        super(x, y, 15, "Nurloc", "Plants", Color.GREEN);
    }

    public Creature reproduce() {
        if (age > 5 && health > 5) {
            return new Nurloc(x, y, 15);
        }
        return null;
    }
}
