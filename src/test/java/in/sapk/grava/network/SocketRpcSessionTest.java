package in.sapk.grava.network;

import org.junit.Test;

/**
 * Created by george on 04/06/15.
 */
public class SocketRpcSessionTest {

    @Test(expected = NullPointerException.class)
    public void testCtor_badArg() throws NullPointerException {
        new SocketRpcSession(null);
    }
}