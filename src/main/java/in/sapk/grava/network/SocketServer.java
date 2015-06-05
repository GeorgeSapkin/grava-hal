package in.sapk.grava.network;

/**
 * Created by george on 01/06/15.
 */

import in.sapk.grava.rpc.RpcServer;
import in.sapk.grava.rpc.RpcServerFactory;
import in.sapk.grava.rpc.RpcSession;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SocketServer class is the WebSocket endpoint of the game server.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
 */
@ServerEndpoint("/grava")
@SuppressWarnings("unused")
public class SocketServer {

    private static final Logger LOGGER = Logger.getLogger(SocketServer.class.getName());

    private final RpcServer rpcServer;

    public SocketServer() {
        // TODO: should be injected instead
        RpcServerFactory rpcServerFactory = new RpcServerFactory();
        rpcServer = rpcServerFactory.getRpcServer();
    }

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info(session.getId() + " has started");

        RpcSession rpcSession = new SocketRpcSession(session);
        rpcServer.onOpen(rpcSession);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.log(Level.FINE, session.getId() + ": message " + message);

        RpcSession rpcSession = new SocketRpcSession(session);
        rpcServer.onMessage(message, rpcSession);
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.info(session.getId() + " has ended");

        RpcSession rpcSession = new SocketRpcSession(session);
        rpcServer.onClose(rpcSession);
    }
}