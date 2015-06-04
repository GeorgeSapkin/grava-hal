package in.sapk.grava.rpc;

import in.sapk.grava.game.Pits;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;
import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.server.GameTransport;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by george on 04/06/15.
 */
public class RpcGameTransportTest {

    public GameTransport transport;

    @Before
    public void setUp() {
        RpcSession session = mock(RpcSession.class);
        RpcProtocol protocol = mock(RpcProtocol.class);
        transport = new RpcGameTransport(session, protocol);
    }

    @Test(expected = NullPointerException.class)
    public void testCtor_badArg_session() throws NullPointerException {
        new RpcGameTransport(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testCtor_badArg_protocol() throws NullPointerException {
        RpcSession session = mock(RpcSession.class);
        new RpcGameTransport(session, null);
    }

    @Test
    public void testCtor() {
        RpcSession session = mock(RpcSession.class);
        RpcProtocol protocol = mock(RpcProtocol.class);
        new RpcGameTransport(session, protocol);
    }

    @Test(expected = NullPointerException.class)
    public void testNotify_badArg_turn() throws NullPointerException {
        transport.notify(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testCtor_badArg_pits() throws NullPointerException {
        Turn turn = new Turn(Side.A, new Pits());
        transport.notify(turn, null);
    }
}