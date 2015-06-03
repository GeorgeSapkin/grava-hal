package in.sapk.grava.server;

// TODO: add onStarted

import in.sapk.grava.game.Game;
import in.sapk.grava.game.Pits;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;

/**
 * Created by george on 27/05/15.
 */
public class GameSession {

    private Game game;

    private GameTransport transportA;
    private GameTransport transportB;

    private Turn currentTurn;

    public GameSession() {
        game = new Game();
    }

    public boolean isFull() {
        boolean isFull = transportA != null && transportB != null;
        return isFull;
    }

    public void join(GameTransport transport) {
        if (isFull())
            throw new IllegalStateException("Cannot join a full session");

        if (transportA == null) {
            transportA = transport;
            transportA.notifyLogin(Side.A);
        }
        else {
            transportB = transport;
            transportB.notifyLogin(Side.B);
        }

        if (isFull()) {
            currentTurn = game.start();

            notifySides();
        }
    }

    public void sow(final String sessionId, final int idx) {
        if (currentTurn == null)
            throw new IllegalStateException("Other side has not joined yet");

        Side side = getSideFromSession(sessionId);
        if (currentTurn.getSide() != side)
            throw new IllegalStateException("Other side's turn");

        currentTurn = currentTurn.sow(idx);

        notifySides();
    }

    private void notifySides() {
        Pits pitsA = new Pits(Side.A, currentTurn.getPits());
        transportA.notify(currentTurn, pitsA);

        Pits pitsB = pitsA.getOpposite();
        transportB.notify(currentTurn, pitsB);
    }

    private Side getSideFromSession(final String sessionId) {
        if (transportA.getId() == sessionId)
            return Side.A;
        else if (transportB.getId() == sessionId)
            return Side.B;

        throw new IllegalStateException(
                "Side for session " + sessionId + " not found");
    }
}
