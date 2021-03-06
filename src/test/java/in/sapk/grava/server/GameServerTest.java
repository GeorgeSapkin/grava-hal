package in.sapk.grava.server;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by george on 04/06/15.
 */
public class GameServerTest {

    private GameServer server;

    @Before
    public void setUp() {
        server = new GameServer();
    }

    @Test(expected = NullPointerException.class)
    public void testJoin_badArg() throws NullPointerException {
        server.join(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSow_badArg_sessionNull() throws IllegalArgumentException {
        server.sow(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSow_badArg_sessionEmpty() throws IllegalArgumentException {
        server.sow("", 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testSow_badState() throws IllegalStateException {
        server.sow("someId", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeave_badArg_sessionNull() throws IllegalArgumentException {
        server.leave(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeave_badArg_sessionEmpty() throws IllegalArgumentException {
        server.leave("");
    }

}