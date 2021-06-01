package expression;

import java.util.Objects;

public abstract class BinaryOperations implements CommonExpression, TripleExpression {
    private CommonExpression leftSide;
    private CommonExpression rightSide;

    public BinaryOperations(CommonExpression leftSide, CommonExpression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    protected abstract int priority();
    protected abstract String getOperator();
    protected abstract int operate(int x, int y);

    @Override
    public String toString() {
        return "(" + leftSide.toString() + " " + getOperator() + " " + rightSide.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return operate(this.leftSide.evaluate(x),
                this.rightSide.evaluate(x));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof BinaryOperations) {

            return ((BinaryOperations) obj).leftSide.equals(this.leftSide) &&
                    ((BinaryOperations) obj).rightSide.equals(this.rightSide) &&
                    ((BinaryOperations) obj).getOperator().equals(this.getOperator());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.leftSide, this.rightSide, getOperator());
    }

    private String getVar(CommonExpression var, int x) {

        if (var instanceof BinaryOperations && (
                (this.priority() > ((BinaryOperations) var).priority()) ||
                        (x == 1 &&
                                this.getOperator().equals("-") &&
                                ((BinaryOperations) var).priority() == 0) ||
                        (x == 1 && ((this.getOperator().equals("/") &&
                                (((BinaryOperations) var).getOperator().equals("/") ||
                                        ((BinaryOperations) var).getOperator().equals("*"))) ||
                                (this.getOperator().equals("*") && ((BinaryOperations) var).getOperator().equals("/"))))
                )
        ) {
            return "(" + var.toMiniString() + ")";
        }
        return var.toMiniString();
    }

    @Override
    public String toMiniString() {
        return getVar(this.leftSide, 0) + " " + getOperator() + " " + getVar(this.rightSide, 1);
    }

    @Override
    public int evaluate(int x, int y, int z) {
            return operate(this.leftSide.evaluate(x, y, z),
                    this.rightSide.evaluate(x, y, z));
    }
}
