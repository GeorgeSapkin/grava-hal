package in.sapk.grava.rpc.protocol;

import java.util.Collections;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * RPC method call information.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
 */
public class RpcMethod {

    private final String id;
    private final String name;
    private final Map<String, String> params;

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
        return Collections.unmodifiableMap(params);
    }
}
