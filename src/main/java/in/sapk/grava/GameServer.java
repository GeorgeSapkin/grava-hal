package in.sapk.grava;

import in.sapk.grava.Transport;
import in.sapk.grava.Session;

/**
 * Created by george on 27/05/15.
 */
public class GameServer {

    private Transport transport;

    private Session[] sessions;

    public GameServer(Transport transport) {
        this.transport = transport;
    }
}
