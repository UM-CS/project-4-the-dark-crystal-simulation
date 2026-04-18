package creatures;

import definitions.Creature;

public class Landstrider extends Creature {

    public Landstrider(int x, int y) {
        super(x, y, 10, "Landstrider", "Plants");
        
    }

    public Creature reproduce() {
        if (age > 5 && health > 5) {
            return new Landstrider(x, y);
        }
        return null;
    }
    
}
