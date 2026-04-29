package creatures;
import java.awt.Color;

import definitions.Creature;

public class Fizzgig extends Creature {

    public Fizzgig(int x, int y, int size) {
        super(x, y, 10, "Fizzgig", "Bugs", Color.ORANGE);
    }

    public Creature reproduce() {
        if (age > 3 && health > 5) {
            return new Fizzgig(x, y,15);
        }
        return null;
    }
    
}
