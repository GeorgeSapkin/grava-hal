package in.sapk.grava.game;

import static com.google.common.base.Preconditions.*;

/**
 * Created by george on 28/05/15.
 */
public class Turn {

    private static final int HALF_STONES = 36;

    private final Side side;
    private final Pits pits;
    private final TurnType type;

    @SuppressWarnings("SameParameterValue")
    public Turn(Side side, Pits pits) {
        this(side, pits, TurnType.PLAYER);
    }

    public Turn(Side side, Pits pits, TurnType type) {
        checkNotNull(pits, "pits cannot be null");

        this.side = side;
        this.pits = new Pits(side, pits);
        this.type = type;
    }

    public Side getSide() {
        return side;
    }

    public Pits getPits() {
        return pits;
    }

    public TurnType getType() {
        return type;
    }

    public Turn sow(final int pitIdx) {
        checkElementIndex(pitIdx, Pits.SIDE_PIT_COUNT, "pitIdx must be in [0; 5]");

        Pit srcPit = pits.get(pitIdx);

        int stones = srcPit.clearStones();
        checkState(stones > 0, "Cannot move stones from an empty pit");

        Pit gravaHal = pits.getGravaHal();

        // get global pit index
        int nextPitIdx = pitIdx;

        boolean bonusTurn = false;
        while (stones != 0) {
            ++nextPitIdx;
            Pit destPit = pits.get(nextPitIdx);

            boolean canPlace = destPit.canPlaceFrom(side);
            if (!canPlace) {
                continue;
            }

            boolean lastStone = stones == 1;
            if (lastStone) {
                boolean isGravaHal = destPit == gravaHal;
                if (isGravaHal) {
                    bonusTurn = true;
                } else { // not grava hal

                    // if destination pit is own and empty

                    int destStones = destPit.getStones();
                    boolean sameSide = destPit.getSide() == side;
                    boolean destEmpty = destStones == 0;
                    if (sameSide && destEmpty) {

                        // move stone to grava hal
                        gravaHal.addStone();

                        // and move opposite pit's stones to grava hal
                        Pit opPit = destPit.getOpposite();
                        int opStones = opPit.clearStones();
                        gravaHal.addStones(opStones);

                        // last stone moved, nothing else to do
                        break;
                    }
                }
            }

            destPit.addStone();
            --stones;
        }

        TurnType nextTurnType = TurnType.PLAYER;
        if (gravaHal.getStones() > HALF_STONES) {
            nextTurnType = TurnType.GAME_OVER;
        }

        // if either side pits are empty
        if (pits.arePlayerPitsEmpty()) {
            // move remaining stones to respective grava hals
            pits.clearPits();
            nextTurnType = TurnType.GAME_OVER;
        }

        Side nextSide = side;
        // if game over, check which side has more stones
        if (nextTurnType == TurnType.GAME_OVER) {
            nextSide = gravaHal.getStones() > gravaHal.getOpposite().getStones() ? side : side.getOpposite();
        } else if (!bonusTurn) {
            nextSide = side.getOpposite();
        }
        // else same side

        return new Turn(nextSide, pits, nextTurnType);
    }
}
