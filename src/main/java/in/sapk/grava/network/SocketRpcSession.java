package in.sapk.grava.network;

import in.sapk.grava.rpc.RpcSession;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by george on 03/06/15.
 */
class SocketRpcSession implements RpcSession {

    private Session session;

    public SocketRpcSession(final Session session) {
        if (session == null)
            throw new IllegalArgumentException("session cannot be null");

        this.session = session;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void sendText(String text) throws IOException {
        System.out.println(text);

        session.getBasicRemote().sendText(text);
    }

    @Override
    public void close() throws IOException {
        session.close();
    }
}
