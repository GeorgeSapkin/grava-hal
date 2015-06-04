package in.sapk.grava.game;

/**
 * Created by george on 27/05/15.
 */
public class Game {

    private final Pits pits;

    public Game() {
        pits = new Pits();
    }

    public Pits getPits() {
        return pits;
    }

    public Turn start() {
        return new Turn(Side.A, pits, TurnType.PLAYER);
    }
}
