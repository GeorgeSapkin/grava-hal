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

@ServerEndpoint("/grava")
@SuppressWarnings("unused")
public class SocketServer {

    private final RpcServer rpcServer;

    public SocketServer() {
        // TODO: should be injected instead
        RpcServerFactory rpcServerFactory = new RpcServerFactory();
        rpcServer = rpcServerFactory.getRpcServer();
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " has opened a connection");

        RpcSession rpcSession = new SocketRpcSession(session);
        rpcServer.onOpen(rpcSession);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);

        RpcSession rpcSession = new SocketRpcSession(session);
        rpcServer.onMessage(message, rpcSession);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");

        RpcSession rpcSession = new SocketRpcSession(session);
        rpcServer.onClose(rpcSession);
    }
}