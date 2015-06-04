package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by george on 04/06/15.
 */
public class PitsIteratorTest {

    private static final int TOTAL_PIT_COUNT = 14;

    private Iterator<Pit> pitsIterator;

    @Before
    public void setUp() throws Exception {
        Pits pits = new Pits();
        pitsIterator = pits.iterator();
    }

    @Test
    public void testNext() throws Exception {
        for (int i = TOTAL_PIT_COUNT - 1; i >= 0; --i) {
            assertTrue(pitsIterator.hasNext());
            assertNotNull(pitsIterator.next());
        }
        assertFalse(pitsIterator.hasNext());
    }
}