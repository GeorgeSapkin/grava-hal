package in.sapk.grava.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * The Board class contains board related logic along with all the pits and a
 * projection of a board from a specified side's point of view.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-30
 */
public class Board implements Iterable<Pit> {

    static final int TOTAL_PIT_COUNT = 14;
    private static final int SIDE_A_OFFSET = 0;
    private static final int SIDE_B_OFFSET = 7;
    private static final int GRAVA_HAL_OFFSET = 6;
    static final int SIDE_PIT_COUNT = 6;
    private static final int SIDE_TOTAL_PIT_COUNT = 7;

    private final List<Pit> pits;
    private Side side;
    private int offset;
    private int gravaHalIdx;

    public Board() {
        this.pits = Collections.synchronizedList(new ArrayList<>(TOTAL_PIT_COUNT));

        setSide(Side.A);

        initSide(Side.A);
        initSide(Side.B);

        initOpposites();
    }

    public Board(final Side side, final Board other) {
        this(side, other.pits);
    }

    private Board(final Side side, final List<Pit> other) {
        this.pits = other;

        setSide(side);
    }

    private static boolean arePlayerPitsEmpty(Board board) {
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            int stones = board.get(i).getStones();
            if (stones != 0) {
                return false;
            }
        }
        return true;
    }

    private static void clearPits(Board board) {
        Pit gravaHal = board.getGravaHal();
        for (int i = SIDE_PIT_COUNT - 1; i >= 0; --i) {
            int stones = board.get(i).clearStones();
            gravaHal.addStones(stones);
        }
    }

    private void setSide(final Side side) {
        this.side = side;
        this.offset = (side == Side.A) ? SIDE_A_OFFSET : SIDE_B_OFFSET;
        this.gravaHalIdx = offset + GRAVA_HAL_OFFSET;
    }

    /**
     * Returns the pit for a specified index in board side's view.
     * @param index Index of a pit in board side's view.
     * @return The pit for a specified index in board side's view.
     */
    public Pit get(final int index) {
        int realIdx = getIndex(index);
        return pits.get(realIdx);
    }

    private void set(final int index, Pit pit) {
        int realIdx = getIndex(index);

        if (realIdx >= pits.size())
            pits.add(realIdx, pit);
        else
            pits.set(realIdx, pit);
    }

    public Pit getGravaHal() {
        return pits.get(gravaHalIdx);
    }

    private void setGravaHal(Pit pit) {
        if (gravaHalIdx >= pits.size())
            pits.add(gravaHalIdx, pit);
        else
            pits.set(gravaHalIdx, pit);
    }

    public Board getOpposite() {
        return new Board(side.getOpposite(), this);
    }

    public boolean arePlayerPitsEmpty() {
        Board boardA = new Board(Side.A, this);
        boolean sideAEmpty = arePlayerPitsEmpty(boardA);

        Board boardB = boardA.getOpposite();
        boolean sideBEmpty = arePlayerPitsEmpty(boardB);

        return sideAEmpty || sideBEmpty;
    }

    /**
     * Moves remaining stones to respective grava hals.
     */
    public void clearPits() {
        Board boardA = new Board(Side.A, this);
        clearPits(boardA);

        Board boardB = boardA.getOpposite();
        clearPits(boardB);
    }

    private int getIndex(final int idx) {
        return (offset + idx) % TOTAL_PIT_COUNT;
    }

    private void initSide(final Side side) {
        Board sideBoard = new Board(side, pits);
        for (int i = 0; i < SIDE_PIT_COUNT; ++i) {
            sideBoard.set(i, new Pit(side));
        }

        sideBoard.setGravaHal(new GravaHal(side));
    }

    private void initOpposites() {
        for (int idx = SIDE_PIT_COUNT - 1; idx >= 0; --idx) {
            final int opIdx = SIDE_TOTAL_PIT_COUNT + SIDE_PIT_COUNT - idx - 1;

            Pit pitA = pits.get(idx);
            Pit pitB = pits.get(opIdx);
            pitA.setOpposite(pitB);
            pitB.setOpposite(pitA);
        }

        final int grIdx = GRAVA_HAL_OFFSET;
        final int opGrIdx = SIDE_TOTAL_PIT_COUNT + GRAVA_HAL_OFFSET;
        pits.get(grIdx).setOpposite(pits.get(opGrIdx));
        pits.get(opGrIdx).setOpposite(pits.get(grIdx));
    }

    @Override
    public Iterator<Pit> iterator() {
        return new BoardIterator(this);
    }
}
