package in.sapk.grava.game;

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
