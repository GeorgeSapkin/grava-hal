package in.sapk.grava.rpc;

import in.sapk.grava.rpc.protocol.json.JsonRpcProtocol;

/**
 * Created by george on 03/06/15.
 */
public class RpcServerFactory {

    private static GameRpcServer instance;

    static {
        if (instance == null) {
            instance = new GameRpcServer(new JsonRpcProtocol());
        }
    }

    public RpcServer getRpcServer() {
        return instance;
    }
}
