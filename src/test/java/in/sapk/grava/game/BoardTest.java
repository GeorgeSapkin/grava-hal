package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 31/05/15.
 */
public class BoardTest {

    private Board boardA;
    private Board boardB;

    @Before
    public void setUp() {
        boardA = new Board();
        boardB = boardA.getOpposite();
    }

    @Test
    public void testGetGravaHal() {
        Pit pit6     = boardA.get(6);
        Pit gravaHal = boardA.getGravaHal();
        assertEquals(pit6, gravaHal);
    }

    @Test
    public void testArePlayerPitsEmpty_sideA() {
        boardA.get(0).clearStones();
        boardA.get(1).clearStones();
        boardA.get(2).clearStones();
        boardA.get(3).clearStones();
        boardA.get(4).clearStones();
        boardA.get(5).clearStones();

        assertTrue(boardA.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideB() {
        boardB.get(0).clearStones();
        boardB.get(1).clearStones();
        boardB.get(2).clearStones();
        boardB.get(3).clearStones();
        boardB.get(4).clearStones();
        boardB.get(5).clearStones();

        assertTrue(boardB.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideANotEmpty() {
        boardA.get(0).clearStones();
        boardA.get(1).clearStones();
        boardA.get(2).clearStones();
        boardA.get(4).clearStones();
        boardA.get(5).clearStones();

        assertFalse(boardA.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideBNotEmpty() {
        boardB.get(0).clearStones();
        boardB.get(1).clearStones();
        boardB.get(2).clearStones();
        boardB.get(4).clearStones();
        boardB.get(5).clearStones();

        assertFalse(boardA.arePlayerPitsEmpty());
    }

    @Test
    public void testClearPits() {
        boardA.clearPits();

        assertEquals(0, boardA.get(0).getStones());
        assertEquals(0, boardA.get(1).getStones());
        assertEquals(0, boardA.get(2).getStones());
        assertEquals(0, boardA.get(3).getStones());
        assertEquals(0, boardA.get(4).getStones());
        assertEquals(0, boardA.get(5).getStones());

        assertEquals(0, boardB.get(0).getStones());
        assertEquals(0, boardB.get(1).getStones());
        assertEquals(0, boardB.get(2).getStones());
        assertEquals(0, boardB.get(3).getStones());
        assertEquals(0, boardB.get(4).getStones());
        assertEquals(0, boardB.get(5).getStones());
    }
}