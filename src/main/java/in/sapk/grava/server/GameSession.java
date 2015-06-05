package in.sapk.grava.server;

import in.sapk.grava.game.Board;
import in.sapk.grava.game.Game;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by george on 27/05/15.
 */
class GameSession {

    private final Game game;

    private GameTransport transportA;
    private GameTransport transportB;
    private Turn currentTurn;

    public GameSession() {
        game = new Game();
    }

    public String getIdA() {
        return transportA.getId();
    }

    public String getIdB() {
        return transportB.getId();
    }

    public boolean isFull() {
        return (transportA != null) && (transportB != null);
    }

    public void join(GameTransport transport) {
        checkNotNull(transport, "transport cannot be null");
        checkState(!isFull(), "Cannot join a full session");

        if (transportA == null) {
            transportA = transport;
            transportA.notifyLogin(Side.A);
        } else {
            transportB = transport;
            transportB.notifyLogin(Side.B);
        }

        if (isFull()) {
            currentTurn = game.start();

            notifySides();
        }
    }

    public void sow(final String sessionId, final int idx) {
        checkArgument(!isNullOrEmpty(sessionId), "sessionId cannot be null or empty");
        checkState(currentTurn != null, "Other side has not joined yet");

        Side side = getSideFromSession(sessionId);
        checkState(currentTurn.getSide() == side, "Other side's turn");

        currentTurn = currentTurn.sow(idx);

        notifySides();
    }

    public void close() {
        if (transportA != null) {
            transportA.close();
        }

        if (transportB != null) {
            transportB.close();
        }
    }

    private void notifySides() {
        Board boardA = new Board(Side.A, currentTurn.getBoard());
        transportA.notify(currentTurn, boardA);

        Board boardB = boardA.getOpposite();
        transportB.notify(currentTurn, boardB);
    }

    private Side getSideFromSession(final String sessionId) {
        if (transportA.getId().equals(sessionId)) {
            return Side.A;
        } else if (transportB.getId().equals(sessionId)) {
            return Side.B;
        }

        throw new IllegalStateException(
                "Side for session " + sessionId + " not found");
    }
}
