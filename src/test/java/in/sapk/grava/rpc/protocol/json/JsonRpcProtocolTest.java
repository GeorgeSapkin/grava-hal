package in.sapk.grava.rpc.protocol.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import in.sapk.grava.rpc.protocol.RpcMethod;
import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.rpc.protocol.RpcProtocolException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by george on 04/06/15.
 */
public class JsonRpcProtocolTest {

    private static final String JSON_RPC_VERSION = "2.0";
    private RpcProtocol protocol;

    @Before
    public void setUp() {
        protocol = new JsonRpcProtocol();
    }

    @Test
    public void testGetError_nullNull() {
        String json = protocol.getError(null, null);
        Gson gson = new Gson();
        JsonRpcError<String> error = gson.fromJson(json, JsonRpcError.class);

        assertEquals(JSON_RPC_VERSION, error.jsonrpc);
        assertNull(error.id);
        assertNotNull(error.error);
        assertNull(error.error.message);
    }

    @Test
    public void testGetError_idNull() {
        String id = "1";

        String json = protocol.getError(id, null);
        Gson gson = new Gson();
        JsonRpcError<String> error = gson.fromJson(json, JsonRpcError.class);

        assertEquals(JSON_RPC_VERSION, error.jsonrpc);
        assertEquals(id, error.id);
        assertNotNull(error.error);
        assertNull(error.error.message);
    }

    @Test
    public void testGetError_idMessage() {
        String id = "1";
        String message = "something bad";

        String json = protocol.getError(id, message);
        Gson gson = new Gson();
        JsonRpcError<String> error = gson.fromJson(json, JsonRpcError.class);

        assertEquals(JSON_RPC_VERSION, error.jsonrpc);
        assertEquals(id, error.id);
        assertNotNull(error.error);
        assertEquals(message, error.error.message);
    }

    @Test
    public void testGetError_nullMessage() {
        String message = "something bad";

        String json = protocol.getError(null, message);
        Gson gson = new Gson();
        JsonRpcError<String> error = gson.fromJson(json, JsonRpcError.class);

        assertEquals(JSON_RPC_VERSION, error.jsonrpc);
        assertNull(error.id);
        assertNotNull(error.error);
        assertEquals(message, error.error.message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNotification_badArg() throws IllegalArgumentException {
        protocol.getNotification(null, null);
    }

    @Test
    public void testGetNotification_methodNull() {
        String method = "sow";
        String json = protocol.getNotification(method, null);
        Gson gson = new Gson();
        JsonRpcNotification<Map<String, String>> notification = gson.fromJson(json, JsonRpcNotification.class);

        assertEquals(JSON_RPC_VERSION, notification.jsonrpc);
        assertEquals(method, notification.method);
        assertNull(notification.params);
    }

    @Test
    public void testGetNotification_methodParams() {
        String method = "sow";
        Map<String, String> params = new HashMap<>();
        params.put("index", "1");

        String json = protocol.getNotification(method, params);
        Gson gson = new Gson();
        JsonRpcNotification<Map<String, String>> notification = gson.fromJson(json, JsonRpcNotification.class);

        assertEquals(JSON_RPC_VERSION, notification.jsonrpc);
        assertEquals(method, notification.method);
        assertNotNull(notification.params);
        assertTrue(notification.params.containsKey("index"));
        assertEquals("1", notification.params.get("index"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMethod_badArg_null() throws IllegalArgumentException, RpcProtocolException {
        protocol.getMethod(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMethod_badArg_empty() throws IllegalArgumentException, RpcProtocolException {
        protocol.getMethod("");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidJsonRpcMethod1() throws RpcProtocolException {
        protocol.getMethod("{}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidJsonRpcMethod2() throws RpcProtocolException {
        protocol.getMethod("{");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidJsonRpcVersion() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"1.0\"}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidId_undefined() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"2.0\"}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidId_null() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"2.0\",\"id\":null}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidId_empty() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"2.0\",\"id\":\"\"}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidMethod_undefined() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"2.0\",\"id\":\"1\"}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidMethod_null() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":null}");
    }

    @Test(expected = RpcProtocolException.class)
    public void testGetMethod_badArg_invalidMethod_empty() throws RpcProtocolException {
        protocol.getMethod("{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"\"}");
    }

    @Test
    public void testGetMethod() throws RpcProtocolException {
        RpcMethod method = protocol.getMethod("{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"sow\",\"params\":{\"index\":\"2\"}}");

        assertNotNull(method);
        assertEquals("sow", method.getName());
        assertEquals(1, method.getParams().size());
        assertTrue(method.getParams().containsKey("index"));
        assertEquals("2", method.getParams().get("index"));
        assertEquals("1", method.getId());
    }
}