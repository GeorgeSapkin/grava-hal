package in.sapk.grava.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by george on 27/05/15.
 */
public class SideTest {

    @Test
    public void testGetValue_A() {
        Side side = Side.A;
        assertEquals(0, side.getValue());
    }

    @Test
    public void testGetValue_B() {
        Side side = Side.B;
        assertEquals(1, side.getValue());
    }

    @Test
    public void testGetOpposite_A() {
        Side side = Side.A;
        assertEquals(Side.B, side.getOpposite());
    }

    @Test
    public void testGetOpposite_B() {
        Side side = Side.B;
        assertEquals(Side.A, side.getOpposite());
    }
}