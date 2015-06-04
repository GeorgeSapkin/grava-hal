package in.sapk.grava.rpc;

import in.sapk.grava.rpc.protocol.JsonRpcProtocol;

/**
 * Created by george on 03/06/15.
 */
public class RpcServerFactory {

    private static final Object instanceLock = new Object();
    private static GameRpcServer instance;

    public GameRpcServer getRpcServer() {
        synchronized (instanceLock) {
            if (instance == null)
                instance = new GameRpcServer(new JsonRpcProtocol());
        }
        return instance;
    }
}
