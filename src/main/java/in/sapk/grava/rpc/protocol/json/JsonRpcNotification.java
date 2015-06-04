package in.sapk.grava.rpc.protocol.json;

/**
 * Created by george on 04/06/15.
 */
@SuppressWarnings("unused")
class JsonRpcNotification<T> extends JsonRpcMessage {
    public String method;
    public T params;
}
