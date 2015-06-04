package in.sapk.grava.network;

import in.sapk.grava.rpc.RpcSession;

import javax.websocket.Session;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by george on 03/06/15.
 */
class SocketRpcSession implements RpcSession {

    private final Session session;

    public SocketRpcSession(final Session session) {
        checkNotNull(session, "session cannot be null");

        this.session = session;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void sendMessage(String text) throws IOException {
        session.getBasicRemote().sendText(text);
    }

    @Override
    public void close() throws IOException {
        session.close();
    }
}
