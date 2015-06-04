package in.sapk.grava.rpc;

/**
 * Created by george on 03/06/15.
 */
public interface RpcServer {
    void onOpen(RpcSession session);
    void onMessage(String message, RpcSession session);
    void onClose(RpcSession session);
}
