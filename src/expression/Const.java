package expression;

import java.util.Objects;

public class Const implements CommonExpression {
    private int number;

    public Const(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public String toMiniString() {
        return Integer.toString(number);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return number;
    }

    @Override
    public int evaluate(int x) {
        return number;
    }

    @Override
    public boolean equals(Object n) {

        if (n instanceof Const) {
            return ((Const) n).number == number;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }
}
