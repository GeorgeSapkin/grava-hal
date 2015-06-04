package in.sapk.grava.rpc.protocol;

import in.sapk.grava.rpc.RpcMethod;

import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

// TODO: should be injected instead?

/**
 * Created by george on 02/06/15.
 */
public class JsonRpcProtocol implements RpcProtocol {

    private static final String JSON_RPC_KEY = "jsonrpc";
    private static final String JSON_RPC_VALUE = "2.0";
    private static final String ID_KEY = "id";
    private static final String METHOD_KEY = "method";
    private static final String ERROR_KEY = "error";
    private static final String MESSAGE_KEY = "message";
    private static final String PARAMS_KEY = "params";

    private static JsonObject readJson(String message) throws RpcProtocolException {
        JsonObject result;
        try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
            result = jsonReader.readObject();
        } catch (JsonException | IllegalStateException e) {
            throw new RpcProtocolException(null, e.getMessage());
        }

        return result;
    }

    private static void validate(JsonObject obj) throws RpcProtocolException {
        checkNotNull(obj, "obj cannot be null");

        String version = obj.getString(JSON_RPC_KEY, null);
        if (!JSON_RPC_VALUE.equals(version)) {
            throw new RpcProtocolException(null, JSON_RPC_KEY + " value must be " + JSON_RPC_VALUE);
        }

        String id = obj.getString(ID_KEY, null);
        if (isNullOrEmpty(id)) {
            throw new RpcProtocolException(null, ID_KEY + " cannot be null or empty");
        }

        String method = obj.getString(METHOD_KEY, null);
        if (isNullOrEmpty(method)) {
            throw new RpcProtocolException(id, METHOD_KEY + " cannot be null or empty");
        }
    }

    @Override
    public String getError(final String id, final String error) {
        JsonObjectBuilder msgBuilder = Json.createObjectBuilder();
        msgBuilder.add(JSON_RPC_KEY, JSON_RPC_VALUE);

        if (id != null) {
            msgBuilder.add(ID_KEY, id);
        } else {
            msgBuilder.addNull(ID_KEY);
        }

        JsonObjectBuilder errBuilder = Json.createObjectBuilder();
        if (error != null) {
            errBuilder.add(MESSAGE_KEY, error);
        } else {
            errBuilder.addNull(MESSAGE_KEY);
        }

        msgBuilder.add(ERROR_KEY, errBuilder);

        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(writer);

        JsonObject msgObj = msgBuilder.build();
        jsonWriter.writeObject(msgObj);
        jsonWriter.close();

        return writer.toString();
    }

    @Override
    public String getNotification(final String method, final Map<String, String> params) {
        JsonObjectBuilder msgBuilder = Json.createObjectBuilder();
        msgBuilder.add(JSON_RPC_KEY, JSON_RPC_VALUE);

        if (method != null) {
            msgBuilder.add(METHOD_KEY, method);
        } else {
            msgBuilder.addNull(METHOD_KEY);
        }

        JsonObjectBuilder paramsBuilder = Json.createObjectBuilder();
        params.forEach((k, v) -> {
            if (v != null) {
                paramsBuilder.add(k, v);
            } else {
                paramsBuilder.addNull(k);
            }
        });

        msgBuilder.add(PARAMS_KEY, paramsBuilder);

        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(writer);

        JsonObject msgObj = msgBuilder.build();
        jsonWriter.writeObject(msgObj);
        jsonWriter.close();

        return writer.toString();
    }

    @Override
    // TODO: should be generic
    public String getNotification(String method, List<String> params) {
        JsonObjectBuilder msgBuilder = Json.createObjectBuilder();
        msgBuilder.add(JSON_RPC_KEY, JSON_RPC_VALUE);

        if (method != null) {
            msgBuilder.add(METHOD_KEY, method);
        } else {
            msgBuilder.addNull(METHOD_KEY);
        }

        JsonArrayBuilder paramsBuilder = Json.createArrayBuilder();
        params.forEach(paramsBuilder::add);
        msgBuilder.add(PARAMS_KEY, paramsBuilder);

        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(writer);

        JsonObject msgObj = msgBuilder.build();
        jsonWriter.writeObject(msgObj);
        jsonWriter.close();

        return writer.toString();
    }

    @Override
    public RpcMethod getMethod(final String message) throws RpcProtocolException {
        JsonObject obj;
        try {
            obj = readJson(message);
        } catch (IllegalArgumentException e) {
            throw new RpcProtocolException(null, e.getMessage());
        }

        try {
            validate(obj);
        } catch (NullPointerException e) {
            throw new RpcProtocolException(null, e.getMessage());
        }

        String method = obj.getString(METHOD_KEY);
        String id = obj.getString(ID_KEY);

        JsonObject paramsObj = null;
        JsonValue paramsVal = obj.get(PARAMS_KEY);
        if (paramsVal.getValueType() == JsonValue.ValueType.OBJECT) {
            paramsObj = obj.getJsonObject(PARAMS_KEY);
        }

        Map<String, String> params = new HashMap<>();
        if (paramsObj != null) {
            paramsObj.forEach((k, v) -> {
                if (v.getValueType() == JsonValue.ValueType.STRING) {
                    params.put(k, ((JsonString) v).getString());
                } else {
                    params.put(k, v.toString());
                }
            });
        }

        return new RpcMethod(method, params, id);
    }
}

