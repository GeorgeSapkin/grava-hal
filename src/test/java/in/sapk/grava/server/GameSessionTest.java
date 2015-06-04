package in.sapk.grava.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by george on 04/06/15.
 */
public class GameSessionTest {

    private GameSession session;

    @Before
    public void setUp() {
        session = new GameSession();
    }

    @Test
    public void testGetIdA() {
        GameTransport transportA = mock(GameTransport.class);
        when(transportA.getId()).thenReturn("idA");

        GameTransport transportB = mock(GameTransport.class);

        session.join(transportA);
        session.join(transportB);

        assertEquals(transportA.getId(), session.getIdA());
    }

    @Test
    public void testGetIdB() {
        GameTransport transportA = mock(GameTransport.class);

        GameTransport transportB = mock(GameTransport.class);
        when(transportB.getId()).thenReturn("idB");

        session.join(transportA);
        session.join(transportB);

        assertEquals(transportB.getId(), session.getIdB());
    }

    @Test
    public void testIsFull_true() {
        GameTransport transportA = mock(GameTransport.class);
        GameTransport transportB = mock(GameTransport.class);

        session.join(transportA);
        session.join(transportB);

        assertTrue(session.isFull());
    }

    @Test
    public void testIsFull_false() {
        GameTransport transportA = mock(GameTransport.class);

        session.join(transportA);

        assertFalse(session.isFull());
    }

    @Test(expected = NullPointerException.class)
    public void testJoin_badArg() throws NullPointerException {
        session.join(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testJoin_full() throws IllegalStateException {
        GameTransport transportA = mock(GameTransport.class);
        GameTransport transportB = mock(GameTransport.class);
        GameTransport transportC = mock(GameTransport.class);

        session.join(transportA);
        session.join(transportB);
        session.join(transportC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSow_badArg_null() throws IllegalArgumentException {
        session.sow(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSow_badArg_empty() throws IllegalArgumentException {
        session.sow("", 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testSow_badState_currentTurnIsNull() throws IllegalStateException {
        session.sow("someId", 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testSow_badState_wrongSide() throws IllegalStateException {
        GameTransport transportA = mock(GameTransport.class);
        when(transportA.getId()).thenReturn("idA");

        GameTransport transportB = mock(GameTransport.class);
        when(transportB.getId()).thenReturn("idB");

        session.join(transportA);
        session.join(transportB);

        session.sow("idB", 0);
    }
}