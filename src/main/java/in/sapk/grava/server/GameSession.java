package in.sapk.grava.server;

import in.sapk.grava.game.Game;
import in.sapk.grava.game.Pits;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
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
        return transportA != null && transportB != null;
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
        if (currentTurn.getSide() != side) {
            throw new IllegalStateException("Other side's turn");
        }

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
        Pits pitsA = new Pits(Side.A, currentTurn.getPits());
        transportA.notify(currentTurn, pitsA);

        Pits pitsB = pitsA.getOpposite();
        transportB.notify(currentTurn, pitsB);
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
