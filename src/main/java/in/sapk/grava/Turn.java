package in.sapk.grava;

/**
 * Created by george on 28/05/15.
 */
public class Turn {

    private Side side;

    public Side getSide() {
        return side;
    }

    private Pits pits;

    public Pits getPits() {
        return pits;
    }

    public Turn(Side side, Pits pits) {
        if (pits == null)
            throw new IllegalArgumentException("pits cannot be null");

        this.side = side;
        this.pits = new Pits(side, pits);
    }

    public Turn sow(final int pitIdx) {
        if (pitIdx < 0 || pitIdx > 5)
            throw new IllegalArgumentException("pitIdx must be in [0; 5]");

        Pit srcPit = pits.get(pitIdx);

        int stones = srcPit.clearStones();
        if (stones == 0)
            throw new IllegalStateException(
                    "Cannot move stones from empty pit");

        Pit gravaHal = pits.getGravaHal();

        // get global pit index
        int nextPitIdx = pitIdx;

        boolean bonusTurn = false;
        while (stones != 0) {
            ++nextPitIdx;
            Pit destPit = pits.get(nextPitIdx);

            boolean canPlace = destPit.canPlaceFrom(side);
            if (!canPlace)
                continue;

            boolean lastStone = stones == 1;
            if (lastStone)
            {
                boolean isGravaHal = destPit == gravaHal;
                if (isGravaHal)
                    bonusTurn = true;
                else { // not grava hal

                    // if destination pit is own and empty

                    int destStones    = destPit.getStones();
                    boolean sameSide  = destPit.getSide() == side;
                    boolean destEmpty = destStones == 0;
                    if (sameSide && destEmpty) {

                        // move stone to grava hal
                        gravaHal.addStone();

                        // and move opposite pit's stones to grava hal
                        Pit opPit    = destPit.getOpposite();
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

        // if either side pits are empty
        if (pits.arePlayerPitsEmpty()) {
            // move remaining stones to respective grava hals
            pits.clearPits();

            // game over,no next turn
            return null;
        }

        Side nextSide = !bonusTurn ? side.getOpposite() : side;
        Turn nextTurn = new Turn(nextSide, pits);
        return nextTurn;
    }
}
