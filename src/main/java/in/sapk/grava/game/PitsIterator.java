package in.sapk.grava.game;

import java.util.Iterator;

/**
 * Created by george on 03/06/15.
 */
class PitsIterator implements Iterator<Pit> {

    private final Pits pits;
    private int idx;

    public PitsIterator(Pits pits) {
        this.pits = pits;
        this.idx  = 0;
    }

    @Override
    public boolean hasNext() {
        return idx < Pits.TOTAL_PIT_COUNT;
    }

    @Override
    public Pit next() {
        return pits.get(idx++);
    }
}
