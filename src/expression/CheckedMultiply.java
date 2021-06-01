package expression;

import expression.exceptions.OverflowException;
import expression.exceptions.UnderflowException;

public class CheckedMultiply extends BinaryOperations {

    public CheckedMultiply(CommonExpression leftSide, CommonExpression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    protected int priority() {
        return 1;
    }

    @Override
    protected String getOperator() {
        return "*";
    }

    @Override
    protected int operate(int x, int y) {

        if (x == 0 || y == 0) {
            return x * y;
        }

        if (x > 0 && y > 0 && x > Integer.MAX_VALUE / y) {
            throw new OverflowException();
        }

        if (x < 0 && y < 0 && x < Integer.MAX_VALUE / y) {
            throw new OverflowException();
        }

        if (x > 0 && y < 0 && y < Integer.MIN_VALUE / x) {
            throw new UnderflowException();
        }

        if(x < 0 && y > 0 && x < Integer.MIN_VALUE / y) {
            throw new UnderflowException();
        }

        return x * y;
    }
}
