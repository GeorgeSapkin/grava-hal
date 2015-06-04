package in.sapk.grava.rpc;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by george on 03/06/15.
 */
public class RpcMethod {

    private String id;
    private String name;
    private Map<String, String> params;

    public RpcMethod(
            final String name,
            final Map<String, String> params,
            final String id) {

        checkArgument(!isNullOrEmpty(name), "name cannot be null or empty");

        this.name = name;
        this.params = params;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
