package in.sapk.grava;

/**
 * Created by george on 27/05/15.
 */
public class GravaHal extends Pit {

    private static final int INITIAL_STONE_COUNT = 0;

    public GravaHal(final Side side) {
        super(side, INITIAL_STONE_COUNT);
    }

    public GravaHal(final Side side, final int stones) {
        super(side, stones);
    }

    @Override
    public boolean canPlaceFrom(final Side side) {
        boolean canPlace = getSide() == side;
        return canPlace;
    }
}
