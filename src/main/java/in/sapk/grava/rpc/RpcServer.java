package in.sapk.grava.rpc;

/**
 * The RpcServer interface for classes that implement the actual server logic.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
 */
public interface RpcServer {
    void onOpen(RpcSession session);

    void onMessage(String message, RpcSession session);

    void onClose(RpcSession session);
}
