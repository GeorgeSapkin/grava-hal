package in.sapk.grava.rpc.protocol.json;

/**
 * Created by george on 04/06/15.
 */
public class JsonRpcError<K> extends JsonRpcMessage {

    public static class Error {
        public String message;
    }

    public K id;

    public Error error = new Error();
}
