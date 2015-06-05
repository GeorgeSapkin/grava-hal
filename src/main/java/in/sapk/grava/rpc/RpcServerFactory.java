package in.sapk.grava.rpc;

import in.sapk.grava.rpc.protocol.json.JsonRpcProtocol;

/**
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
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
