package in.sapk.grava.game;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 27/05/15.
 */
public class PitTest {

    private static final int INITIAL_STONE_COUNT = 6;

    @Test
    public void testCtor_default() {
        Pit pit = new Pit(Side.A);
        int stones = pit.getStones();
        assertEquals(INITIAL_STONE_COUNT, stones);
    }

    @Test
    public void testCtor_arg() {
        Pit pit = new Pit(Side.A, 7);
        int stones = pit.getStones();
        assertEquals(7, stones);
    }

    @Test
    public void testAddStone() {
        Pit pit = new Pit(Side.A);
        pit.addStone();
        int stones = pit.getStones();
        assertEquals(7, stones);
    }
    @Test
    public void testClearStones() {
        Pit pit = new Pit(Side.A);
        pit.clearStones();
        int stones = pit.getStones();
        assertEquals(0, stones);
    }

    @Test
    public void testAddStones_arg() {
        Pit pit = new Pit(Side.A);
        pit.addStones(2);
        int stones = pit.getStones();
        assertEquals(8, stones);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddStones_badArg() throws IllegalArgumentException {
        Pit pit = new Pit(Side.A);
        pit.addStones(-1);
    }

    @Test
    public void testSetOpposite() {
        Pit pit   = new Pit(Side.A);
        Pit opPit = new Pit(Side.B);
        pit.setOpposite(opPit);
        Pit opPit2 = pit.getOpposite();
        assertNotNull(opPit);
        assertEquals(opPit, opPit2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCtor_badArg() throws IllegalArgumentException {
        new Pit(Side.A, -1);
    }

    @Test
    public void testCanPlaceFrom_sameSideIsTrue() {
        Pit pit = new Pit(Side.A);
        Side side = pit.getSide();
        assertTrue(pit.canPlaceFrom(side));
    }

    @Test
    public void testCanPlaceFrom_oppositeSideIsTrue() {
        Pit pit = new Pit(Side.A);
        Side side = pit.getSide().getOpposite();
        assertTrue(pit.canPlaceFrom(side));
    }
}