package in.sapk.grava.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by george on 04/06/15.
 */
public class GameServerTest {

    private GameServer server;

    @Before
    public void setUp() throws Exception {
        server = new GameServer();
    }

    @Test(expected = NullPointerException.class)
    public void testJoin_badArg() throws NullPointerException {
        server.join(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testSow_badArg() throws IllegalStateException {
        server.sow(null, 0);
    }
}