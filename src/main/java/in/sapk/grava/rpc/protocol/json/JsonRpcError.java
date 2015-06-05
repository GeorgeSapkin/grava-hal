package in.sapk.grava.rpc.protocol.json;

/**
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-04
 */
@SuppressWarnings("unused")
class JsonRpcError<K> extends JsonRpcMessage {

    public static class Error {
        public String message;
    }

    public K id;

    public Error error = new Error();
}
