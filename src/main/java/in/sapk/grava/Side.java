package in.sapk.grava;

public enum Side {
    A (0),
    B (1);

    private final int value;
    public int getValue() {
        return value;
    }

    Side(final int value) {
        this.value = value;
    }

    public Side getOpposite() {
        Side opposite = value == A.value ? B : A;
        return opposite;
    }
}
