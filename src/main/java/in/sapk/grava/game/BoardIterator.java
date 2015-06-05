package in.sapk.grava.game;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The BoardIterator class implements Iterator\<Pit\> over the pits from a board.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-03
 */
class BoardIterator implements Iterator<Pit> {

    private final Board board;
    private int idx;

    public BoardIterator(Board board) {
        this.board = board;
        this.idx = 0;
    }

    @Override
    public boolean hasNext() {
        return idx < Board.TOTAL_PIT_COUNT;
    }

    @Override
    public Pit next() {
        if (!hasNext())
            throw new NoSuchElementException();

        Pit result = board.get(idx);
        idx++;
        return result;
    }
}
