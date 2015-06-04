package in.sapk.grava.rpc.protocol;

/**
 * Created by george on 03/06/15.
 */
public class RpcProtocolException extends Exception {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    private final String id;

    public RpcProtocolException(String message) {
        super(message);

        this.id = null;
    }

    public RpcProtocolException(Throwable cause) {
        super(cause);

        this.id = null;
    }

    @SuppressWarnings("SameParameterValue")
    public RpcProtocolException(String message, Throwable cause) {
        super(message, cause);

        this.id = null;
    }

    @SuppressWarnings("SameParameterValue")
    public RpcProtocolException(String id, String message) {
        super(message);

        this.id = id;
    }

    public String getId() {
        return id;
    }
}
