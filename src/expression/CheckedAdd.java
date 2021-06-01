package expression;

import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryOperations {

    public CheckedAdd(CommonExpression leftSide, CommonExpression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    protected int priority() {
        return 0;
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    protected int operate(int x, int y) {

        if ((x > 0 && y > 0 && Integer.MAX_VALUE - x < y) ||
                (x < 0 && y < 0 && Integer.MIN_VALUE - x > y)) {
            throw new OverflowException();
        }
        return x + y;
    }
}
