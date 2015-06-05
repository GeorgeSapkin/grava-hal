package in.sapk.grava.server;

import in.sapk.grava.game.Board;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;

/**
 * The GameTransport interface to abstract RPC session from GameSever.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-27
 */
public interface GameTransport {
    String getId();

    /**
     * Sends login notification with session ID and assigned side
     *
     * @param side Assigned side
     */
    void notifyLogin(Side side);

    void notify(Turn turn, Board board);

    void close();
}
