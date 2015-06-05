package in.sapk.grava.rpc.protocol.json;

import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.rpc.protocol.RpcProtocolException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by george on 04/06/15.
 */
public class JsonRpcProtocolTest {

    private RpcProtocol protocol;

    @Before
    public void setUp() {
        protocol = new JsonRpcProtocol();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNotification_badArg() throws IllegalArgumentException {
        protocol.getNotification(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMethod_badArg_null() throws IllegalArgumentException, RpcProtocolException {
        protocol.getMethod(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMethod_badArg_empty() throws IllegalArgumentException, RpcProtocolException {
        protocol.getMethod("");
    }
}