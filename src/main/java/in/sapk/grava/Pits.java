package in.sapk.grava;

/**
 * Created by george on 30/05/15.
 */
public class Pits {

    private static final int SIDE_A_OFFSET        = 0;
    private static final int SIDE_B_OFFSET        = 7;
    private static final int GRAVA_HAL_OFFSET     = 6;
    private static final int TOTAL_PIT_COUNT      = 14;
    private static final int SIDE_PIT_COUNT       = 6;
    private static final int SIDE_TOTAL_PIT_COUNT = 7;

    private Pit[] pits;

    private void setSide(final Side side) {
        this.offset = (side == Side.A) ? SIDE_A_OFFSET : SIDE_B_OFFSET;
        this.gravaHalIdx = offset + GRAVA_HAL_OFFSET;
    }

    private int offset;
    private int gravaHalIdx;

    public Pits() {
        this(Side.A);
    }

    public Pits(final Side side, final Pits other) {
        this.pits = other.pits;

        setSide(side);
    }

    public Pits(final Side side) {
        this.pits = new Pit[TOTAL_PIT_COUNT];

        setSide(side);

        initSide(Side.A);
        initSide(Side.B);

        initOpposites();
    }

    public Pit get(int index) {
        int realIdx = getIndex(index);
        return pits[realIdx];
    }

    public Pit getGravaHal() {
        return pits[gravaHalIdx];
    }

    public boolean arePlayerPitsEmpty() {
        boolean sideAEmpty = true;
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            int stones = pits[i].getStones();
            if (stones != 0) {
                sideAEmpty = false;
                break;
            }
        }

        boolean sideBEmpty = true;
        for (int i = SIDE_B_OFFSET + SIDE_PIT_COUNT - 1; i >= SIDE_B_OFFSET; --i) {
            int stones = pits[i].getStones();
            if (stones != 0) {
                sideBEmpty = false;
                break;
            }
        }

        return sideAEmpty || sideBEmpty;
    }

    /**
     * Moves remaining stones to respective grava hals
     */
    public void clearPits() {
        Pit gravaHalA = pits[GRAVA_HAL_OFFSET];
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            int stones = pits[i].clearStones();
            gravaHalA.addStones(stones);
        }

        Pit gravaHalB = pits[SIDE_B_OFFSET + GRAVA_HAL_OFFSET];
        for (int i = SIDE_B_OFFSET + SIDE_PIT_COUNT - 1; i >= SIDE_B_OFFSET; --i) {
            int stones = pits[i].clearStones();
            gravaHalB.addStones(stones);
        }
    }

    private int getIndex(final int idx) {
        int result = (offset + idx) % TOTAL_PIT_COUNT;
        return result;
    }

    private void initSide(final Side side) {
        int offset = (side == Side.A) ? SIDE_A_OFFSET : SIDE_B_OFFSET;

        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i)
            pits[offset + i] = new Pit(side);

        pits[offset + GRAVA_HAL_OFFSET] = new GravaHal(side);
    }

    private void initOpposites() {
        for (int idx = SIDE_PIT_COUNT; idx >= 0; --idx) {
            final int opIdx = idx + SIDE_TOTAL_PIT_COUNT;

            Pit pitA = pits[idx];
            Pit pitB = pits[opIdx];
            pitA.setOpposite(pitB);
            pitB.setOpposite(pitA);
        }
    }
}
