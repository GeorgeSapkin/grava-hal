package in.sapk.grava.rpc.protocol.json;

/**
 * JSON-RPC notification
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-04
 */
@SuppressWarnings("unused")
class JsonRpcNotification<T> extends JsonRpcMessage {
    public String method;
    public T params;
}
