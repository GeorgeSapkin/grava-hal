package in.sapk.grava.game;

/**
 * The GravaHal class extends the Pit class with grava hal-specific logic.
 *
 * Grava Hal is a special pit where players have to accumulate their stones to win.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-27
 */
class GravaHal extends Pit {

    private static final int INITIAL_STONE_COUNT = 0;

    public GravaHal(final Side side) {
        super(side, INITIAL_STONE_COUNT);
    }

    /**
     * Check if it is possible to place a stone into a Grava Hal from a provided side.
     * @param side Side to check.
     * @return True if it is possible to put a stone from a provided side, otherwise - false.
     */
    @Override
    public boolean canPlaceFrom(final Side side) {
        return getSide() == side;
    }
}
