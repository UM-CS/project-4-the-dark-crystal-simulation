package creatures;
import definitions.Creature;

public class Fizzgig extends Creature {

    public Fizzgig(int x, int y) {
        super(x, y, 10, "Fizzgig", "Bugs");
    }

    public Creature reproduce() {
        if (age > 3 && health > 5) {
            return new Fizzgig(x, y);
        }
        return null;
    }
    
}
