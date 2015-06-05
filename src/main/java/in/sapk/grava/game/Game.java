package in.sapk.grava.game;

/**
 * The Game class is a starting point for a Grava Hal game.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-27
 */
public class Game {

    private final Board board;

    public Game() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Starts the game.
     * @return The first turn of the game.
     */
    public Turn start() {
        return new Turn(Side.A, board, TurnType.PLAYER);
    }
}
