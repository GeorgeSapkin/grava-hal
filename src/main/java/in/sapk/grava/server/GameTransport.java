package in.sapk.grava.server;

import in.sapk.grava.game.Pits;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;

/**
 * Created by george on 27/05/15.
 */
public interface GameTransport {
    String getId();

    /**
     * Sends login notification with session ID and assigned side
     *
     * @param side Assigned side
     */
    void notifyLogin(Side side);

    void notify(Turn turn, Pits pits);

    void close();
}
