package in.sapk.grava.rpc.protocol;

import org.junit.Test;

/**
 * Created by george on 04/06/15.
 */
public class RpcMethodTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCtor_badArg_null() throws IllegalArgumentException {
        new RpcMethod(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCtor_badArg_empty() throws IllegalArgumentException {
        new RpcMethod("", null, null);
    }
}