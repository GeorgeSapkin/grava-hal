package in.sapk.grava.rpc.protocol;

import in.sapk.grava.rpc.RpcMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by george on 03/06/15.
 */
public interface RpcProtocol {
    String getError(final String id, final String error);
    String getNotification(final String method, final Map<String, String> params);
    String getNotification(final String method, final List<String> params);
    RpcMethod getMethod(final String message) throws RpcProtocolException;
}
