package creatures;

import definitions.Creature;

public class Nurloc extends Creature {
    
    public Nurloc(int x, int y) {
        super(x, y, 15, "Nurloc", "Plants");
    }

    public Creature reproduce() {
        if (age > 5 && health > 5) {
            return new Nurloc(x, y);
        }
        return null;
    }
}
