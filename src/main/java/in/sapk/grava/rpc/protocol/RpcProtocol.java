package in.sapk.grava.rpc.protocol;

/**
 * Created by george on 03/06/15.
 */
public interface RpcProtocol {
    <T> String getError(final T id, final String errorMessage);

    <T> String getNotification(final String method, final T params);

    RpcMethod getMethod(final String message) throws RpcProtocolException;
}
