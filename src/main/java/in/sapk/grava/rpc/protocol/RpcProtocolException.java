package in.sapk.grava.rpc.protocol;

/**
 * Created by george on 03/06/15.
 */
public class RpcProtocolException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String id;

    public RpcProtocolException(final String id, final String message) {
        super(message);

        this.id = id;
    }

    public RpcProtocolException(String message) {
        super(message);

        this.id = null;
    }

    public RpcProtocolException(Throwable cause) {
        super(cause);

        this.id = null;
    }

    public String getId() {
        return id;
    }
}
