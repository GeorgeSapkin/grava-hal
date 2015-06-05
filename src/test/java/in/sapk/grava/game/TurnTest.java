package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by george on 28/05/15.
 */
public class TurnTest {

    private Board boardA;
    private Board boardB;

    @Before
    public void setUp() {
        boardA = new Board();
        boardB = boardA.getOpposite();
    }

    @Test(expected = NullPointerException.class)
    public void testCtor_badArg() throws NullPointerException {
        new Turn(Side.A, null);
    }

    @Test
    public void testGetPits() {
        Turn turn = new Turn(Side.A, boardA);

        Board board2 = turn.getBoard();
        assertNotNull(board2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMove_badArgMinus1() throws IndexOutOfBoundsException {
        Turn turn = new Turn(Side.A, boardA);
        turn.sow(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMove_badArg6() throws IndexOutOfBoundsException {
        Turn turn = new Turn(Side.A, boardA);
        turn.sow(6);
    }

    @Test(expected = IllegalStateException.class)
    public void testMove_noStones() throws IllegalStateException {
        boardA.get(0).clearStones();
        Turn turn = new Turn(Side.A, boardA);
        turn.sow(0);
    }

    @Test
    public void testMove_bonusTurn() {
        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(0);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(0, boardA.get(0).getStones());
        assertEquals(7, boardA.get(1).getStones());
        assertEquals(7, boardA.get(2).getStones());
        assertEquals(7, boardA.get(3).getStones());
        assertEquals(7, boardA.get(4).getStones());
        assertEquals(7, boardA.get(5).getStones());
        assertEquals(1, boardA.getGravaHal().getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.A, nextSide);
    }

    @Test
    public void testMove_nextSide() {
        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(1);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(6, boardA.get(0).getStones());
        assertEquals(0, boardA.get(1).getStones());
        assertEquals(7, boardA.get(2).getStones());
        assertEquals(7, boardA.get(3).getStones());
        assertEquals(7, boardA.get(4).getStones());
        assertEquals(7, boardA.get(5).getStones());
        assertEquals(1, boardA.getGravaHal().getStones());

        assertEquals(7, boardB.get(0).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_skipOpponentsGravaHal() {
        boardA.get(5).addStones(2);

        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(7, boardA.get(0).getStones());
        assertEquals(0, boardA.get(5).getStones());
        assertEquals(1, boardA.getGravaHal().getStones());

        assertEquals(7, boardB.get(0).getStones());
        assertEquals(7, boardB.get(1).getStones());
        assertEquals(7, boardB.get(2).getStones());
        assertEquals(7, boardB.get(3).getStones());
        assertEquals(7, boardB.get(4).getStones());
        assertEquals(7, boardB.get(5).getStones());
        assertEquals(0, boardB.getGravaHal().getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_ownEmptyPit() {
        boardA.get(0).clearStones();
        boardA.get(5).addStones(2);

        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(0, boardA.get(0).getStones());
        assertEquals(0, boardA.get(5).getStones());
        assertEquals(9, boardA.getGravaHal().getStones());

        assertEquals(7, boardB.get(0).getStones());
        assertEquals(7, boardB.get(1).getStones());
        assertEquals(7, boardB.get(2).getStones());
        assertEquals(7, boardB.get(3).getStones());
        assertEquals(7, boardB.get(4).getStones());
        assertEquals(0, boardB.get(5).getStones());
        assertEquals(0, boardB.getGravaHal().getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_opponentsEmptyPit() {
        boardB.get(0).clearStones();

        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(1);

        assertNotNull(turn2);
        assertEquals(TurnType.PLAYER, turn2.getType());

        assertEquals(0, boardA.get(1).getStones());
        assertEquals(7, boardA.get(2).getStones());
        assertEquals(7, boardA.get(3).getStones());
        assertEquals(7, boardA.get(4).getStones());
        assertEquals(7, boardA.get(5).getStones());
        assertEquals(1, boardA.getGravaHal().getStones());

        assertEquals(1, boardB.get(0).getStones());

        Side nextSide = turn2.getSide();
        assertEquals(Side.B, nextSide);
    }

    @Test
    public void testMove_gameOver_clearSideB() {
        boardA.get(0).clearStones();
        boardA.get(1).clearStones();
        boardA.get(2).clearStones();
        boardA.get(3).clearStones();
        boardA.get(4).clearStones();
        boardA.getGravaHal().addStones(30);

        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.GAME_OVER, turn2.getType());
        assertEquals(Side.B, turn2.getSide());

        assertEquals(0,  boardA.get(0).getStones());
        assertEquals(0,  boardA.get(1).getStones());
        assertEquals(0,  boardA.get(2).getStones());
        assertEquals(0,  boardA.get(3).getStones());
        assertEquals(0,  boardA.get(4).getStones());
        assertEquals(0,  boardA.get(5).getStones());
        assertEquals(31, boardA.getGravaHal().getStones());

        assertEquals(0,  boardB.get(0).getStones());
        assertEquals(0,  boardB.get(1).getStones());
        assertEquals(0,  boardB.get(2).getStones());
        assertEquals(0,  boardB.get(3).getStones());
        assertEquals(0,  boardB.get(4).getStones());
        assertEquals(0,  boardB.get(5).getStones());
        assertEquals(41, boardB.getGravaHal().getStones());
    }

    @Test
    public void testMove_gameOver_clearSideA() {
        boardB.get(0).clearStones();
        boardB.get(1).clearStones();
        boardB.get(2).clearStones();
        boardB.get(3).clearStones();
        boardB.get(4).clearStones();
        boardB.getGravaHal().addStones(30);

        Turn turn1 = new Turn(Side.B, boardB);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.GAME_OVER, turn2.getType());
        assertEquals(Side.A, turn2.getSide());

        assertEquals(0,  boardA.get(0).getStones());
        assertEquals(0,  boardA.get(1).getStones());
        assertEquals(0,  boardA.get(2).getStones());
        assertEquals(0,  boardA.get(3).getStones());
        assertEquals(0,  boardA.get(4).getStones());
        assertEquals(0,  boardA.get(5).getStones());
        assertEquals(41, boardA.getGravaHal().getStones());

        assertEquals(0,  boardB.get(0).getStones());
        assertEquals(0,  boardB.get(1).getStones());
        assertEquals(0,  boardB.get(2).getStones());
        assertEquals(0,  boardB.get(3).getStones());
        assertEquals(0,  boardB.get(4).getStones());
        assertEquals(0,  boardB.get(5).getStones());
        assertEquals(31, boardB.getGravaHal().getStones());
    }

    @Test
    public void testMove_gameOver_draw() {
        boardA.get(0).clearStones();
        boardA.get(1).clearStones();
        boardA.get(2).clearStones();
        boardA.get(3).clearStones();
        boardA.get(4).clearStones();
        boardA.get(5).clearStones();
        boardA.get(5).addStones(1);
        boardA.getGravaHal().addStones(35);

        boardB.get(0).clearStones();
        boardB.get(1).clearStones();
        boardB.get(2).clearStones();
        boardB.get(3).clearStones();
        boardB.get(4).clearStones();
        boardB.get(5).clearStones();
        boardB.get(5).addStones(1);
        boardB.getGravaHal().addStones(35);

        Turn turn1 = new Turn(Side.A, boardA);
        Turn turn2 = turn1.sow(5);

        assertNotNull(turn2);
        assertEquals(TurnType.DRAW, turn2.getType());

        assertEquals(0,  boardA.get(0).getStones());
        assertEquals(0,  boardA.get(1).getStones());
        assertEquals(0,  boardA.get(2).getStones());
        assertEquals(0,  boardA.get(3).getStones());
        assertEquals(0,  boardA.get(4).getStones());
        assertEquals(0,  boardA.get(5).getStones());
        assertEquals(36, boardA.getGravaHal().getStones());

        assertEquals(0,  boardB.get(0).getStones());
        assertEquals(0,  boardB.get(1).getStones());
        assertEquals(0,  boardB.get(2).getStones());
        assertEquals(0,  boardB.get(3).getStones());
        assertEquals(0,  boardB.get(4).getStones());
        assertEquals(0,  boardB.get(5).getStones());
        assertEquals(36, boardB.getGravaHal().getStones());
    }
}