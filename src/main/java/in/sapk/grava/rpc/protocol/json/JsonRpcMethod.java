package in.sapk.grava.rpc.protocol.json;

import java.util.Map;

/**
 * JSON-RPC method
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-04
 */
@SuppressWarnings("unused")
class JsonRpcMethod {
    public String jsonrpc;
    public String id;
    public String method;
    public Map<String, String> params;
}
