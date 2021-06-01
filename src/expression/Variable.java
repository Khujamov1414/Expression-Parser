package expression;

import java.util.Objects;

public class Variable implements CommonExpression {
    private String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public String toMiniString() {
        return variable;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public boolean equals(Object n) {

        if (n instanceof Variable) {
            return this.variable.equals(((Variable) n).variable);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }

    @Override
    public int evaluate(int x, int y, int z) {

        if (variable.equals("x")) {
            return x;

        } else if (variable.equals("y")) {
            return y;

        } else {
            return z;
        }
    }
}
