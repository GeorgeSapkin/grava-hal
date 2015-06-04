package in.sapk.grava.rpc;

import java.io.IOException;

/**
 * Created by george on 03/06/15.
 */
public interface RpcSession {
    String getId();

    void sendText(String text) throws IOException;

    void close() throws IOException;
}
