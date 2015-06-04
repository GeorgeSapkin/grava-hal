package in.sapk.grava.rpc;

import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.rpc.protocol.RpcProtocolException;
import in.sapk.grava.server.GameServer;
import in.sapk.grava.server.GameTransport;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by george on 03/06/15.
 */
class GameRpcServer implements RpcServer {

    private static final Logger LOGGER = Logger.getLogger(GameRpcServer.class.getName());

    private static final String INDEX_KEY  = "index";
    private static final String SOW_METHOD = "sow";

    private final GameServer gameServer;
    private final RpcProtocol protocol;

    public GameRpcServer(final RpcProtocol protocol) {
        this.protocol = protocol;

        gameServer = new GameServer();
    }

    private static void sendMessage(RpcSession session, final String message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to send RPC message", e);
        }
    }

    @Override
    public void onOpen(RpcSession session) {
        GameTransport transport = new RpcGameTransport(session, protocol);
        gameServer.join(transport);
    }

    @Override
    public void onMessage(String message, RpcSession session) {
        RpcMethod methodCall;
        try {
            methodCall = protocol.getMethod(message);
        } catch (RpcProtocolException e) {
            LOGGER.log(Level.SEVERE, "Failed to get RPC method", e);

            String msg = protocol.getError(e.getId(), e.getMessage());
            sendMessage(session, msg);
            return;
        }
        route(methodCall, session);
    }

    @Override
    public void onClose(RpcSession session) {
        gameServer.leave(session.getId());
    }

    private void route(final RpcMethod methodCall, RpcSession session) {
        if (SOW_METHOD.equals(methodCall.getName())) {
            sow(methodCall, session);
        } else {
            final String errorMsg = "Undefined RPC method " + methodCall.getName();
            LOGGER.warning(session.getId() + ": " + errorMsg);

            String msg = protocol.getError(methodCall.getId(), errorMsg);
            sendMessage(session, msg);
        }
    }

    private void sow(RpcMethod methodCall, RpcSession session) {
        if (!methodCall.getParams().containsKey(INDEX_KEY)) {
            LOGGER.warning(session.getId() + ": index not set");

            String msg = protocol.getError(methodCall.getId(), "index must be set");
            sendMessage(session, msg);
            return;
        }

        String idxStr = methodCall.getParams().get(INDEX_KEY);
        int idx;
        try {
            idx = Integer.parseInt(idxStr);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, session.getId() + ": index not a number", e);

            String msg = protocol.getError(methodCall.getId(), "index must be a number");
            sendMessage(session, msg);
            return;
        }

        try {
            gameServer.sow(session.getId(), idx);
        } catch (IllegalArgumentException|IllegalStateException e) {
            LOGGER.log(Level.WARNING, session.getId() + ": failed to sow", e);

            String msg = protocol.getError(methodCall.getId(), e.getMessage());
            sendMessage(session, msg);
        }
    }
}
