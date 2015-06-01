package in.sapk.grava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 31/05/15.
 */
public class PitsTest {

    private Pits pitsA;
    private Pits pitsB;

    @Before
    public void setUp() throws Exception {
        pitsA = new Pits();
        pitsB = pitsA.getOpposite();
    }

    @Test
    public void testGetGravaHal() {
        Pit pit6     = pitsA.get(6);
        Pit gravaHal = pitsA.getGravaHal();
        assertEquals(pit6, gravaHal);
    }

    @Test
    public void testArePlayerPitsEmpty_sideA() throws Exception {
        pitsA.get(0).clearStones();
        pitsA.get(1).clearStones();
        pitsA.get(2).clearStones();
        pitsA.get(3).clearStones();
        pitsA.get(4).clearStones();
        pitsA.get(5).clearStones();

        assertTrue(pitsA.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideB() throws Exception {
        pitsB.get(0).clearStones();
        pitsB.get(1).clearStones();
        pitsB.get(2).clearStones();
        pitsB.get(3).clearStones();
        pitsB.get(4).clearStones();
        pitsB.get(5).clearStones();

        assertTrue(pitsB.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideANotEmpty() throws Exception {
        pitsA.get(0).clearStones();
        pitsA.get(1).clearStones();
        pitsA.get(2).clearStones();
        pitsA.get(4).clearStones();
        pitsA.get(5).clearStones();

        assertFalse(pitsA.arePlayerPitsEmpty());
    }

    @Test
    public void testArePlayerPitsEmpty_sideBNotEmpty() throws Exception {
        pitsB.get(0).clearStones();
        pitsB.get(1).clearStones();
        pitsB.get(2).clearStones();
        pitsB.get(4).clearStones();
        pitsB.get(5).clearStones();

        assertFalse(pitsA.arePlayerPitsEmpty());
    }

    @Test
    public void testClearPits() throws Exception {
        pitsA.clearPits();

        assertEquals(0, pitsA.get(0).getStones());
        assertEquals(0, pitsA.get(1).getStones());
        assertEquals(0, pitsA.get(2).getStones());
        assertEquals(0, pitsA.get(3).getStones());
        assertEquals(0, pitsA.get(4).getStones());
        assertEquals(0, pitsA.get(5).getStones());

        assertEquals(0, pitsB.get(0).getStones());
        assertEquals(0, pitsB.get(1).getStones());
        assertEquals(0, pitsB.get(2).getStones());
        assertEquals(0, pitsB.get(3).getStones());
        assertEquals(0, pitsB.get(4).getStones());
        assertEquals(0, pitsB.get(5).getStones());
    }
}