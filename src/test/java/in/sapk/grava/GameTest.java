package in.sapk.grava;

import org.junit.*;
import static org.junit.Assert.*;

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

//        for (Pit pit: pits)
//            assertNotNull(pit);
    }

    @Test
    public void start() {
        Turn turn = game.start();
        assertNotNull(turn);
    }
}