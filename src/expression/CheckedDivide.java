package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends BinaryOperations {

    public CheckedDivide(CommonExpression leftSide, CommonExpression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    protected int priority() {
        return 1;
    }

    @Override
    protected String getOperator() {
        return "/";
    }

    @Override
    protected int operate(int x, int y) {

        if (y == 0) {
            throw new DivideByZeroException();
        }

        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
        return x / y;
    }
}
