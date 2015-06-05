package in.sapk.grava.game;

import static com.google.common.base.Preconditions.*;

/**
 * The Turn class contains the core game logic.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-28
 */
public class Turn {

    private static final int HALF_STONES = 36;

    private final Side side;
    private final Board board;
    private final TurnType type;

    @SuppressWarnings("SameParameterValue")
    public Turn(Side side, Board board) {
        this(side, board, TurnType.PLAYER);
    }

    public Turn(Side side, Board board, TurnType type) {
        checkNotNull(board, "board cannot be null");

        this.side = side;
        this.board = new Board(side, board);
        this.type = type;
    }

    public Side getSide() {
        return side;
    }

    public Board getBoard() {
        return board;
    }

    public TurnType getType() {
        return type;
    }

    /**
     * Sows the stones from a specified pit according to game rules.
     * @param pitIdx Index of a pit to sow the stones from.
     * @return Next turn
     */
    public Turn sow(final int pitIdx) {
        checkElementIndex(pitIdx, Board.SIDE_PIT_COUNT, "pitIdx must be in [0; 5]");

        int stones = board.get(pitIdx).clearStones();
        checkState(stones > 0, "Cannot move stones from an empty pit");

        Pit gravaHal = board.getGravaHal();

        int nextPitIdx = pitIdx;

        boolean bonusTurn = false;
        while (stones != 0) {
            ++nextPitIdx;
            final Pit destPit = board.get(nextPitIdx);
            if (!destPit.canPlaceFrom(side)) {
                continue;
            }

            // if last stone
            if (stones == 1) {
                // if destination is grava hal
                if (destPit == gravaHal) {
                    // player gets a bonus turn
                    bonusTurn = true;

                } else if (destPit.isSameSideAndEmpty(side)) {
                    // if destination pit is not grava gal but is own and empty
                    // put stone and opposite opponent's pit's stones into grava hal
                    final int opStones = destPit.getOpposite().clearStones();
                    gravaHal.addStones(opStones + 1);

                    // last stone moved, nothing else to do
                    break;
                }
            }

            destPit.addStone();
            --stones;
        }

        TurnType nextTurnType = TurnType.PLAYER;

        // if either side board are empty
        if (board.arePlayerPitsEmpty()) {
            // move remaining stones to respective grava hals
            board.clearPits();
            nextTurnType = TurnType.GAME_OVER;

        } else if (gravaHal.getStones() > HALF_STONES) {
            // if grava hal has more than half of all the stones
            // then game is over
            nextTurnType = TurnType.GAME_OVER;
        }

        return getNextTurn(nextTurnType, bonusTurn);
    }

    private Turn getNextTurn(TurnType turnType, boolean bonusTurn) {
        TurnType nextTurnType = turnType;
        Side nextSide = side;
        // if game over, check which side has more stones
        if (nextTurnType == TurnType.GAME_OVER) {
            final Pit gravaHal = board.getGravaHal();
            final int thisStones = gravaHal.getStones();
            final int otherStones = gravaHal.getOpposite().getStones();

            // if there is the same amount of stones in both grava hals
            if (thisStones == otherStones) {
                // then this game is a draw
                nextTurnType = TurnType.DRAW;
            } else {
                // else side with most stones is the winner
                nextSide = (thisStones > otherStones) ? side : side.getOpposite();
            }
        } else if (!bonusTurn) {
            nextSide = side.getOpposite();
        }
        // else same side and player turn

        return new Turn(nextSide, board, nextTurnType);
    }
}
