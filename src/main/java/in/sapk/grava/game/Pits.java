package in.sapk.grava.game;

import java.util.Iterator;

/**
 * Created by george on 30/05/15.
 */
public class Pits implements Iterable<Pit> {

    static final int TOTAL_PIT_COUNT = 14;
    private static final int SIDE_A_OFFSET = 0;
    private static final int SIDE_B_OFFSET = 7;
    private static final int GRAVA_HAL_OFFSET = 6;
    private static final int SIDE_PIT_COUNT = 6;
    private static final int SIDE_TOTAL_PIT_COUNT = 7;

    private final Pit[] pitStore;
    private Side side;
    private int offset;
    private int gravaHalIdx;

    public Pits() {
        this.pitStore = new Pit[TOTAL_PIT_COUNT];

        setSide(Side.A);

        initSide(Side.A);
        initSide(Side.B);

        initOpposites();
    }

    public Pits(final Side side, final Pits other) {
        this(side, other.pitStore);
    }

    private Pits(final Side side, final Pit[] other) {
        this.pitStore = other;

        setSide(side);
    }

    private static boolean arePlayerPitsEmpty(Pits pits) {
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            int stones = pits.get(i).getStones();
            if (stones != 0) {
                return false;
            }
        }
        return true;
    }

    private static void clearPits(Pits pits) {
        Pit gravaHal = pits.getGravaHal();
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            int stones = pits.get(i).clearStones();
            gravaHal.addStones(stones);
        }
    }

    private void setSide(final Side side) {
        this.side = side;
        this.offset = (side == Side.A) ? SIDE_A_OFFSET : SIDE_B_OFFSET;
        this.gravaHalIdx = offset + GRAVA_HAL_OFFSET;
    }

    public Pit get(final int index) {
        int realIdx = getIndex(index);
        return pitStore[realIdx];
    }

    private void set(final int index, Pit pit) {
        int realIdx = getIndex(index);
        pitStore[realIdx] = pit;
    }

    public Pit getGravaHal() {
        return pitStore[gravaHalIdx];
    }

    private void setGravaHal(Pit pit) {
        pitStore[gravaHalIdx] = pit;
    }

    public Pits getOpposite() {
        return new Pits(side.getOpposite(), this);
    }

    public boolean arePlayerPitsEmpty() {
        Pits pitsA = new Pits(Side.A, this);
        boolean sideAEmpty = arePlayerPitsEmpty(pitsA);

        Pits pitsB = pitsA.getOpposite();
        boolean sideBEmpty = arePlayerPitsEmpty(pitsB);

        return sideAEmpty || sideBEmpty;
    }

    /**
     * Moves remaining stones to respective grava hals
     */
    public void clearPits() {
        Pits pitsA = new Pits(Side.A, this);
        clearPits(pitsA);

        Pits pitsB = pitsA.getOpposite();
        clearPits(pitsB);
    }

    private int getIndex(final int idx) {
        return (offset + idx) % TOTAL_PIT_COUNT;
    }

    private void initSide(final Side side) {
        Pits sidePits = new Pits(side, pitStore);
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            sidePits.set(i, new Pit(side));
        }

        sidePits.setGravaHal(new GravaHal(side));
    }

    private void initOpposites() {
        for (int idx = SIDE_PIT_COUNT; idx >= 0; --idx) {
            final int opIdx = SIDE_TOTAL_PIT_COUNT + SIDE_PIT_COUNT - idx - 1;

            Pit pitA = pitStore[idx];
            Pit pitB = pitStore[opIdx];
            pitA.setOpposite(pitB);
            pitB.setOpposite(pitA);
        }
    }

    @Override
    public Iterator<Pit> iterator() {
        return new PitsIterator(this);
    }
}
