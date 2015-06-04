package in.sapk.grava.game;

/**
 * Created by george on 27/05/15.
 */
public class Game {

    private final Pits pits;

    public Pits getPits() {
        return pits;
    }

    public Game() {
        pits = new Pits();
    }

    public Turn start() {
        Turn turn = new Turn(Side.A, pits, TurnType.PLAYER);
        return turn;
    }
}
