package in.sapk.grava.rpc.protocol;

/**
 * The RpcProtocol interface to abstract concrete protocol implementations.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
 */
public interface RpcProtocol {
    <T> String getError(final T id, final String errorMessage);

    <T> String getNotification(final String method, final T params);

    RpcMethod getMethod(final String message) throws RpcProtocolException;
}
