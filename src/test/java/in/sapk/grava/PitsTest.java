package in.sapk.grava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 31/05/15.
 */
public class PitsTest {

    private Pits pits;

    @Before
    public void setUp() throws Exception {
        pits = new Pits();
    }

    @Test
    public void testGetGravaHal() {
        Pit pit6     = pits.get(6);
        Pit gravaHal = pits.getGravaHal();
        assertEquals(pit6, gravaHal);
    }

    @Test
    public void testArePlayerPitsEmpty_sideA() throws Exception {
        pits.get(0).clearStones();
        pits.get(1).clearStones();
        pits.get(2).clearStones();
        pits.get(3).clearStones();
        pits.get(4).clearStones();
        pits.get(5).clearStones();

        assertTrue(pits.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideB() throws Exception {
        pits.get(7).clearStones();
        pits.get(8).clearStones();
        pits.get(9).clearStones();
        pits.get(10).clearStones();
        pits.get(11).clearStones();
        pits.get(12).clearStones();

        assertTrue(pits.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideANotEmpty() throws Exception {
        pits.get(0).clearStones();
        pits.get(1).clearStones();
        pits.get(2).clearStones();
        pits.get(4).clearStones();
        pits.get(5).clearStones();

        assertFalse(pits.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideBNotEmpty() throws Exception {
        pits.get(7).clearStones();
        pits.get(8).clearStones();
        pits.get(9).clearStones();
        pits.get(11).clearStones();
        pits.get(12).clearStones();

        assertFalse(pits.arePlayerPitsEmpty());
    }

    @Test
    public void testClearPits() throws Exception {
        pits.clearPits();

        assertEquals(0, pits.get(0).getStones());
        assertEquals(0, pits.get(1).getStones());
        assertEquals(0, pits.get(2).getStones());
        assertEquals(0, pits.get(3).getStones());
        assertEquals(0, pits.get(4).getStones());
        assertEquals(0, pits.get(5).getStones());

        assertEquals(0, pits.get(7).getStones());
        assertEquals(0, pits.get(8).getStones());
        assertEquals(0, pits.get(9).getStones());
        assertEquals(0, pits.get(10).getStones());
        assertEquals(0, pits.get(11).getStones());
        assertEquals(0, pits.get(12).getStones());
    }
}