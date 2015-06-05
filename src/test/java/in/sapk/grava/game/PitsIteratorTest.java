package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by george on 04/06/15.
 */
public class PitsIteratorTest {

    private static final int TOTAL_PIT_COUNT = 14;

    private Iterator<Pit> pitsIterator;

    @Before
    public void setUp() {
        Iterable<Pit> pits = new Pits();
        pitsIterator = pits.iterator();
    }

    @Test
    public void testNext() {
        for (int i = TOTAL_PIT_COUNT - 1; i >= 0; --i) {
            assertTrue(pitsIterator.hasNext());
            assertNotNull(pitsIterator.next());
        }
        assertFalse(pitsIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNext_bad() throws NoSuchElementException {
        for (int i = TOTAL_PIT_COUNT - 1; i >= 0; --i) {
            assertTrue(pitsIterator.hasNext());
            assertNotNull(pitsIterator.next());
        }

        pitsIterator.next();
    }
}