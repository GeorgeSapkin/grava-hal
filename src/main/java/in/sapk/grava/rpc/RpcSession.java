package in.sapk.grava.rpc;

import java.io.IOException;

/**
 * The RpcSession interface to abstract WebSocket session from RpcServer.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
 */
public interface RpcSession {
    String getId();

    void sendMessage(String text) throws IOException;

    void close() throws IOException;
}
