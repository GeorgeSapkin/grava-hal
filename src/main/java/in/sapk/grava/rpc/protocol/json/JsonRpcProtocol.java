package in.sapk.grava.rpc.protocol.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import in.sapk.grava.rpc.protocol.RpcMethod;
import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.rpc.protocol.RpcProtocolException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

// TODO: should be injected instead?

/**
 * Created by george on 02/06/15.
 */
public class JsonRpcProtocol implements RpcProtocol {

    private static final String JSON_RPC_VALUE = "2.0";

    @Override
    public <T> String getError(final T id, final String errorMessage) {
        JsonRpcError<T> message = new JsonRpcError<>();
        message.id = id;
        message.error.message = errorMessage;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(message);
    }

    @Override
    public <T> String getNotification(final String method, final T params) {
        checkArgument(!isNullOrEmpty(method), "method cannot be null or empty");

        JsonRpcNotification<T> message = new JsonRpcNotification<>();
        message.method = method;
        message.params = params;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(message);
    }

    @Override
    public RpcMethod getMethod(final String message) throws RpcProtocolException {
        checkArgument(!isNullOrEmpty(message), "message cannot be null or empty");

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        JsonRpcMethod method;
        try {
            method = gson.fromJson(message, JsonRpcMethod.class);
        } catch (JsonSyntaxException e) {
            throw new RpcProtocolException("Failed to deserialize RPC method", e);
        }

        try {
            validate(method);
        } catch (NullPointerException e) {
            throw new RpcProtocolException(e);
        }

        return new RpcMethod(method.method, method.params, method.id);
    }

    private static void validate(JsonRpcMethod method) throws RpcProtocolException {
        checkNotNull(method, "method cannot be null");

        if (!JSON_RPC_VALUE.equals(method.jsonrpc)) {
            throw new RpcProtocolException("jsonrpc value must be " + JSON_RPC_VALUE);
        }

        if (isNullOrEmpty(method.id)) {
            throw new RpcProtocolException("id cannot be null or empty");
        }

        if (isNullOrEmpty(method.method)) {
            throw new RpcProtocolException(method.id, "method cannot be null or empty");
        }
    }

}

