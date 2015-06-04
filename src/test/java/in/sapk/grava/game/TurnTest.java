package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by george on 28/05/15.
 */
public class TurnTest {

    private Pits pitsA;
    private Pits pitsB;

    @Before
    public void setUp() throws Exception {
        pitsA = new Pits();
        pitsB = pitsA.getOpposite();
    }

    @Test(expected = NullPointerException.class)
    public void testCtor_badArg() throws NullPointerException {
        new Turn(Side.A, null);
    }

    @Test
    public void testGetPits() {
        Turn turn = new Turn(Side.A, pitsA);

        Pits pits2 = turn.getPits();
        assertNotNull(pits2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMove_badArgMinus1() throws IndexOutOfBoundsException {
        Turn turn = new Turn(Side.A, pitsA);
        turn.sow(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMove_badArg6() throws IndexOutOfBoundsException {
        Turn turn = new Turn(Side.A, pitsA);
        turn.sow(6);
    }

    @Test(expected = IllegalStateException.class)
    public void testMove_noStones() throws IllegalStateException {
        pitsA.get(0).clearStones();
        Turn turn = new Turn(Side.A, pitsA);
        turn.sow(0);
    }

    @Test
    public void testMove_bonusTurn() {
        Turn turn1 = new Turn(Side.A, pitsA);
        Turn turn2 = turn1.sow(0);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(0, pitsA.get(0).getStones());
        assertEquals(7, pitsA.get(1).getStones());
        assertEquals(7, pitsA.get(2).getStones());
        assertEquals(7, pitsA.get(3).getStones());
        assertEquals(7, pitsA.get(4).getStones());
        assertEquals(7, pitsA.get(5).getStones());
        assertEquals(1, pitsA.getGravaHal().getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.A, nextSide);
    }

    @Test
    public void testMove_nextSide() {
        Turn turn1 = new Turn(Side.A, pitsA);
        Turn turn2 = turn1.sow(1);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(6, pitsA.get(0).getStones());
        assertEquals(0, pitsA.get(1).getStones());
        assertEquals(7, pitsA.get(2).getStones());
        assertEquals(7, pitsA.get(3).getStones());
        assertEquals(7, pitsA.get(4).getStones());
        assertEquals(7, pitsA.get(5).getStones());
        assertEquals(1, pitsA.getGravaHal().getStones());

        assertEquals(7, pitsB.get(0).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_skipOpponentsGravaHal() {
        pitsA.get(5).addStones(2);

        Turn turn1 = new Turn(Side.A, pitsA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(7, pitsA.get(0).getStones());
        assertEquals(0, pitsA.get(5).getStones());
        assertEquals(1, pitsA.getGravaHal().getStones());

        assertEquals(7, pitsB.get(0).getStones());
        assertEquals(7, pitsB.get(1).getStones());
        assertEquals(7, pitsB.get(2).getStones());
        assertEquals(7, pitsB.get(3).getStones());
        assertEquals(7, pitsB.get(4).getStones());
        assertEquals(7, pitsB.get(5).getStones());
        assertEquals(0, pitsB.getGravaHal().getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_ownEmptyPit() {
        pitsA.get(0).clearStones();
        pitsA.get(5).addStones(2);

        Turn turn1 = new Turn(Side.A, pitsA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(0, pitsA.get(0).getStones());
        assertEquals(0, pitsA.get(5).getStones());
        assertEquals(9, pitsA.getGravaHal().getStones());

        assertEquals(7, pitsB.get(0).getStones());
        assertEquals(7, pitsB.get(1).getStones());
        assertEquals(7, pitsB.get(2).getStones());
        assertEquals(7, pitsB.get(3).getStones());
        assertEquals(7, pitsB.get(4).getStones());
        assertEquals(0, pitsB.get(5).getStones());
        assertEquals(0, pitsB.getGravaHal().getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_opponentsEmptyPit() {
        pitsB.get(0).clearStones();

        Turn turn1 = new Turn(Side.A, pitsA);
        Turn turn2 = turn1.sow(1);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(0, pitsA.get(1).getStones());
        assertEquals(7, pitsA.get(2).getStones());
        assertEquals(7, pitsA.get(3).getStones());
        assertEquals(7, pitsA.get(4).getStones());
        assertEquals(7, pitsA.get(5).getStones());
        assertEquals(1, pitsA.getGravaHal().getStones());

        assertEquals(1, pitsB.get(0).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_gameOver() {
        pitsA.get(0).clearStones();
        pitsA.get(1).clearStones();
        pitsA.get(2).clearStones();
        pitsA.get(3).clearStones();
        pitsA.get(4).clearStones();
        pitsA.getGravaHal().addStones(30);

        Turn turn1 = new Turn(Side.A, pitsA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.GAME_OVER, turn2.getType());

        assertEquals(0,  pitsA.get(0).getStones());
        assertEquals(0,  pitsA.get(1).getStones());
        assertEquals(0,  pitsA.get(2).getStones());
        assertEquals(0,  pitsA.get(3).getStones());
        assertEquals(0,  pitsA.get(4).getStones());
        assertEquals(0,  pitsA.get(5).getStones());
        assertEquals(31, pitsA.getGravaHal().getStones());

        assertEquals(0,  pitsB.get(0).getStones());
        assertEquals(0,  pitsB.get(1).getStones());
        assertEquals(0,  pitsB.get(2).getStones());
        assertEquals(0,  pitsB.get(3).getStones());
        assertEquals(0,  pitsB.get(4).getStones());
        assertEquals(0,  pitsB.get(5).getStones());
        assertEquals(41, pitsB.getGravaHal().getStones());
    }
}