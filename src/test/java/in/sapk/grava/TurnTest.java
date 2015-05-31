package in.sapk.grava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 28/05/15.
 */
public class TurnTest {

    private Pits pits;

    @Before
    public void setUp() throws Exception {
        pits = new Pits();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCtor_badArg() throws IllegalArgumentException {
        new Turn(Side.A, null);
    }

    @Test
    public void testGetPits() {
        Turn turn = new Turn(Side.A, pits);

        Pits pits2 = turn.getPits();
        assertNotNull(pits2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMove_badArgMinus1() throws IllegalArgumentException {
        Turn turn = new Turn(Side.A, pits);
        turn.sow(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMove_badArg6() throws IllegalArgumentException {
        Turn turn = new Turn(Side.A, pits);
        turn.sow(6);
    }

    @Test(expected = IllegalStateException.class)
    public void testMove_noStones() throws IllegalStateException {
        pits.get(0).clearStones();
        Turn turn = new Turn(Side.A, pits);
        turn.sow(0);
    }

    @Test
    public void testMove_bonusTurn() {
        Turn turn1 = new Turn(Side.A, pits);
        Turn turn2 = turn1.sow(0);

        assertNotNull(turn2);

        assertEquals(0, pits.get(0).getStones());
        assertEquals(7, pits.get(1).getStones());
        assertEquals(7, pits.get(2).getStones());
        assertEquals(7, pits.get(3).getStones());
        assertEquals(7, pits.get(4).getStones());
        assertEquals(7, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.A, nextSide);
    }

    @Test
    public void testMove_nextSide() {
        Turn turn1 = new Turn(Side.A, pits);
        Turn turn2 = turn1.sow(1);

        assertNotNull(turn2);

        assertEquals(6, pits.get(0).getStones());
        assertEquals(0, pits.get(1).getStones());
        assertEquals(7, pits.get(2).getStones());
        assertEquals(7, pits.get(3).getStones());
        assertEquals(7, pits.get(4).getStones());
        assertEquals(7, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());
        assertEquals(7, pits.get(7).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_skipOpponentsGravaHal() {
        pits.get(5).addStones(2);

        Turn turn1 = new Turn(Side.A, pits);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);

        assertEquals(7, pits.get(0).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());
        assertEquals(7, pits.get(7).getStones());
        assertEquals(7, pits.get(8).getStones());
        assertEquals(7, pits.get(9).getStones());
        assertEquals(7, pits.get(10).getStones());
        assertEquals(7, pits.get(11).getStones());
        assertEquals(7, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_ownEmptyPit() {
        pits.get(0).clearStones();
        pits.get(5).addStones(2);

        Turn turn1 = new Turn(Side.A, pits);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);

        assertEquals(0, pits.get(0).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(9, pits.get(6).getStones());
        assertEquals(0, pits.get(7).getStones());
        assertEquals(7, pits.get(8).getStones());
        assertEquals(7, pits.get(9).getStones());
        assertEquals(7, pits.get(10).getStones());
        assertEquals(7, pits.get(11).getStones());
        assertEquals(7, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_opponentsEmptyPit() {
        pits.get(7).clearStones();

        Turn turn1 = new Turn(Side.A, pits);
        Turn turn2 = turn1.sow(1);

        assertNotNull(turn2);

        assertEquals(0, pits.get(1).getStones());
        assertEquals(7, pits.get(2).getStones());
        assertEquals(7, pits.get(3).getStones());
        assertEquals(7, pits.get(4).getStones());
        assertEquals(7, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());
        assertEquals(1, pits.get(7).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_gameOver() {
        pits.get(0).clearStones();
        pits.get(1).clearStones();
        pits.get(2).clearStones();
        pits.get(3).clearStones();
        pits.get(4).clearStones();
        pits.get(6).addStones(30);

        Turn turn1 = new Turn(Side.A, pits);
        Turn turn2 = turn1.sow(5);

        assertNull(turn2);

        assertEquals(0, pits.get(0).getStones());
        assertEquals(0, pits.get(1).getStones());
        assertEquals(0, pits.get(2).getStones());
        assertEquals(0, pits.get(3).getStones());
        assertEquals(0, pits.get(4).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(31, pits.get(6).getStones());

        assertEquals(0, pits.get(7).getStones());
        assertEquals(0, pits.get(8).getStones());
        assertEquals(0, pits.get(9).getStones());
        assertEquals(0, pits.get(10).getStones());
        assertEquals(0, pits.get(11).getStones());
        assertEquals(0, pits.get(12).getStones());
        assertEquals(41, pits.get(13).getStones());
    }
}