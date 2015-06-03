package in.sapk.grava.rpc;

import java.util.Map;

/**
 * Created by george on 03/06/15.
 */
public class RpcMethod {

    private String id;

    public String getId() {
        return id;
    }

    private String name;

    public String getName() {
        return name;
    }

    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public RpcMethod(
            final String name,
            final Map<String, String> params,
            final String id) {

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name cannot be null or empty");

        this.name   = name;
        this.params = params;
        this.id     = id;
    }
}
