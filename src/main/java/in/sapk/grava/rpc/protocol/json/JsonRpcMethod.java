package in.sapk.grava.rpc.protocol.json;

import java.util.Map;

/**
 * Created by george on 04/06/15.
 */
@SuppressWarnings("unused")
class JsonRpcMethod {
    public String jsonrpc;
    public String id;
    public String method;
    public Map<String, String> params;
}
