package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;

public class Clorus extends Creature {
    private int r;

    private int g;

    private int b;


    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g , b);
    }

    public void attack(Creature c) {
        energy += c.energy();

    }

    public void move() {
        energy -= 0.03;

    }

    public void stay() {
        energy -= 0.01;
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Clorus replicate() {
        Clorus c_new = new Clorus();
        c_new.energy = this.energy/2;
        energy = energy/2;
        return c_new;
    }

    public Direction getRandomDir(Deque ad){
        Iterator<Direction> seer = ad.iterator();
        int randomIdx = HugLifeUtils.randomInt(0,ad.size()-1);
        int Index = 0;
        Direction dir = (Direction) ad.getFirst();
        while(seer.hasNext() && Index < randomIdx){
            dir = seer.next();
        }
        return dir;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptyNeighbors.add(key);
            }
        }

        boolean anyPlip = true;
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("plip")) {
                plipNeighbors.add(key);
            }
        }
        if (plipNeighbors.size() == 0){
            anyPlip = false;
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            Direction dir = getRandomDir(plipNeighbors);
            return new Action(Action.ActionType.ATTACK, dir);
        } else if (energy >= 1) {
            Direction dir = getRandomDir(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE,dir);
        } else {
            Direction dir = getRandomDir(emptyNeighbors);
            return new Action(Action.ActionType.MOVE,dir);
        }

    }

}
