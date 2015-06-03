package in.sapk.grava.game;

/**
 * Created by george on 27/05/15.
 */
public class Pit {

    private static final int INITIAL_STONE_COUNT = 6;

    private int stones;

    public int getStones() {
        return stones;
    }

    private void setStones(final int value) {
        if (value < 0)
            throw new IllegalArgumentException("value must be positive");

        stones = value;
    }

    public void addStone() {
        ++stones;
    }

    public void addStones(final int value) {
        if (value < 0)
            throw new IllegalArgumentException("value must be positive");

        stones += value;
    }

    public int clearStones() {
        int result = stones;
        stones = 0;
        return result;
    }

    private final Side side;

    public Side getSide() {
        return side;
    }

    private Pit opposite;

    public Pit getOpposite() {
        return opposite;
    }

    public void setOpposite(Pit opposite) {
        this.opposite = opposite;
    }

    public Pit(final Side side) {
        this.side = side;
        setStones(INITIAL_STONE_COUNT);
    }

    public Pit(final Side side, final int stones) {
        this.side = side;
        setStones(stones);
    }

    public boolean canPlaceFrom(final Side side) {
        return true;
    }
}
