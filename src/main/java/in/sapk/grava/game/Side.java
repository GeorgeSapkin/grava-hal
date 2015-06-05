package in.sapk.grava.game;

/**
 * Store the side of a pit or turn.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-05-27
 */
public enum Side {
    A(0),
    B(1);

    private final int value;

    Side(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Side getOpposite() {
        return (value == A.value) ? B : A;
    }
}
