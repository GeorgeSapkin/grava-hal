package in.sapk.grava.game;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The Pit class contains single pit related logic.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-27
 */
public class Pit {

    private static final int INITIAL_STONE_COUNT = 6;

    private final Side side;
    private int stones;
    private Pit opposite;

    public Pit(final Side side) {
        this.side = side;
        setStones(INITIAL_STONE_COUNT);
    }

    public Pit(final Side side, final int stones) {
        this.side = side;
        setStones(stones);
    }

    public int getStones() {
        return stones;
    }

    private void setStones(final int value) {
        checkArgument(value >= 0, "value must be >= 0");

        stones = value;
    }

    public void addStone() {
        ++stones;
    }

    public void addStones(final int value) {
        checkArgument(value >= 0, "value must be >= 0");

        stones += value;
    }

    /**
     * Clears the stones out of the pit.
     * @return Number of cleared stones.
     */
    public int clearStones() {
        int result = stones;
        stones = 0;
        return result;
    }

    public Side getSide() {
        return side;
    }

    /**
     * @return The pit on the opposite side of the board.
     */
    public Pit getOpposite() {
        return opposite;
    }

    void setOpposite(Pit opposite) {
        this.opposite = opposite;
    }

    /**
     * Check if it is possible to place a stone into a pit from a provided side.
     * @param side Side to check.
     * @return Always true, since there are no restriction from which side to place a stone into a pit.
     */
    boolean canPlaceFrom(final Side side) {
        return true;
    }

    boolean isSameSideAndEmpty(final Side side) {
        boolean sameSide = this.side == side;
        boolean destEmpty = this.stones == 0;
        return sameSide && destEmpty;
    }
}
