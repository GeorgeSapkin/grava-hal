package in.sapk.grava.rpc.protocol.json;

/**
 * JSON-RPC message base class
 * 
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-04
 */
@SuppressWarnings("unused")
abstract class JsonRpcMessage {
    private static final String JSON_RPC_VALUE = "2.0";

    public final String jsonrpc = JSON_RPC_VALUE;
}
