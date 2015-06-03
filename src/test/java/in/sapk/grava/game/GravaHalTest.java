package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 28/05/15.
 */
public class GravaHalTest {

    private static final int INITIAL_STONE_COUNT = 0;

    private Pit pit;

    @Before
    public void setUp() throws Exception {
        pit = new GravaHal(Side.A);
    }

    @Test
    public void testCtor_default() {
        int stones = pit.getStones();
        assertEquals(INITIAL_STONE_COUNT, stones);
    }

    @Test
    public void testCanPlaceFrom_sameSideIsTrue() {
        Side side = pit.getSide();
        assertTrue(pit.canPlaceFrom(side));
    }

    @Test
    public void testCanPlaceFrom_oppositeSideIsFalse() {
        Side side = pit.getSide().getOpposite();
        assertFalse(pit.canPlaceFrom(side));
    }
}