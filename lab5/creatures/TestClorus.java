package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the Clorus class
 *  @author yuanzheyang
 */

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }


    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus c_new = c.replicate();
        assertEquals(c.energy(),c_new.energy(),0.01);
        assertEquals(c.color(),c_new.color());



    }



    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip());
        topPlip.put(Direction.BOTTOM, new Impassible());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topPlip);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        //also test when the energy level of Clorus is below one.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE,Direction.TOP);

        assertEquals(expected, actual);


        // Energy <= 1; neighborhood has empty space and there is Plip nearby.
        c = new Clorus(0.5);
        HashMap<Direction, Occupant> LeftPlip = new HashMap<Direction, Occupant>();
        LeftPlip.put(Direction.TOP, new Empty());
        LeftPlip.put(Direction.BOTTOM, new Impassible());
        LeftPlip.put(Direction.LEFT, new Plip());
        LeftPlip.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(LeftPlip);
        Action unexpected = new Action(Action.ActionType.ATTACK,Direction.LEFT);

        assertEquals(unexpected, actual);




        // We don't have Cloruses yet, so we can't test behavior for when they are nearby right now.
    }
}