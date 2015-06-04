package in.sapk.grava.rpc;

import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.rpc.protocol.RpcProtocolException;
import in.sapk.grava.server.GameServer;
import in.sapk.grava.server.GameTransport;

import java.io.IOException;

/**
 * Created by george on 03/06/15.
 */
public class GameRpcServer implements RpcServer {

    private static final String INDEX_KEY  = "index";
    private static final String SOW_METHOD = "sow";

    private final GameServer gameServer;
    private final RpcProtocol protocol;

    public GameRpcServer(final RpcProtocol protocol) {
        this.protocol = protocol;

        gameServer = new GameServer();
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
            String msg = protocol.getError(e.getId(), e.getMessage());
            sendText(session, msg);
            return;
        }
        route(methodCall, session);
    }

    @Override
    public void onClose(RpcSession session) {
        gameServer.leave(session.getId());
    }

    private static void sendText(RpcSession session, final String message) {
        try {
            System.out.println(message);
            session.sendText(message);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void route(final RpcMethod methodCall, RpcSession session) {
        switch (methodCall.getName()) {
            case SOW_METHOD:
                sow(methodCall, session);
                break;
            default: {
                String msg = protocol.getError(methodCall.getId(), "Undefined method " + methodCall.getName());
                sendText(session, msg);
            }
        }
    }

    private void sow(RpcMethod methodCall, RpcSession session) {
        if (!methodCall.getParams().containsKey(INDEX_KEY)) {
            String msg = protocol.getError(methodCall.getId(), "index must be set");
            sendText(session, msg);
            return;
        }

        String idxStr = methodCall.getParams().get(INDEX_KEY);
        int idx;
        try {
            idx = Integer.parseInt(idxStr);
        } catch (NumberFormatException e) {
            String msg = protocol.getError(methodCall.getId(), "index must be a number");
            sendText(session, msg);
            return;
        }

        // TODO: handle exceptions
        try {
            gameServer.sow(session.getId(), idx);
        } catch (IllegalArgumentException|IllegalStateException e) {
            String msg = protocol.getError(methodCall.getId(), e.getMessage());
            sendText(session, msg);
        }
    }
}
