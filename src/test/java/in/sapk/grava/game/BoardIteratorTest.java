package in.sapk.grava.game;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by george on 04/06/15.
 */
public class BoardIteratorTest {

    private static final int TOTAL_PIT_COUNT = 14;

    private Iterator<Pit> boardIterator;

    @Before
    public void setUp() {
        Iterable<Pit> board = new Board();
        boardIterator = board.iterator();
    }

    @Test
    public void testNext() {
        for (int i = TOTAL_PIT_COUNT - 1; i >= 0; --i) {
            assertTrue(boardIterator.hasNext());
            assertNotNull(boardIterator.next());
        }
        assertFalse(boardIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNext_bad() throws NoSuchElementException {
        for (int i = TOTAL_PIT_COUNT - 1; i >= 0; --i) {
            assertTrue(boardIterator.hasNext());
            assertNotNull(boardIterator.next());
        }

        boardIterator.next();
    }
}