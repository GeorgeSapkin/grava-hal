package in.sapk.grava.rpc.protocol.json;

/**
 * Created by george on 04/06/15.
 */
abstract class JsonRpcMessage {
    private static final String JSON_RPC_VALUE = "2.0";

    public final String jsonrpc = JSON_RPC_VALUE;
}
