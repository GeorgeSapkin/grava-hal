package in.sapk.grava.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by george on 27/05/15.
 */
public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void ctor_pitsCountAndNotNull() {
        Pits pits = game.getPits();
        assertNotNull(pits);

        pits.forEach(Assert::assertNotNull);
    }

    @Test
    public void start() {
        Turn turn = game.start();
        assertNotNull(turn);
    }
}