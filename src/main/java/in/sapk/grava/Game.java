package in.sapk.grava;

/**
 * Created by george on 27/05/15.
 */
public class Game {

    private Pits pits;

    public Pits getPits() {
        return pits;
    }

    public Game() {
        pits = new Pits();
    }

    public Turn start() {
        Turn turn = new Turn(Side.A, pits);
        return turn;
    }
}
