package in.sapk.grava.game;

/**
 * Created by george on 27/05/15.
 */
class GravaHal extends Pit {

    private static final int INITIAL_STONE_COUNT = 0;

    public GravaHal(final Side side) {
        super(side, INITIAL_STONE_COUNT);
    }

    @Override
    public boolean canPlaceFrom(final Side side) {
        return getSide() == side;
    }
}
