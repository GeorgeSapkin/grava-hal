package in.sapk.grava.rpc;

import java.io.IOException;

/**
 * Created by george on 03/06/15.
 */
public interface RpcSession {
    String getId();

    void sendMessage(String text) throws IOException;

    void close() throws IOException;
}
