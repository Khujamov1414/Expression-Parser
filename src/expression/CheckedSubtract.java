package expression;

import expression.exceptions.OverflowException;
import expression.exceptions.UnderflowException;

public class CheckedSubtract extends BinaryOperations {

    public CheckedSubtract(CommonExpression leftSide, CommonExpression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    protected int priority() {
        return 0;
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    protected int operate(int x, int y) throws OverflowException, UnderflowException {

        if (x == 0 && y == Integer.MIN_VALUE) {
            throw new OverflowException();
        }

        if ((x >= 0 && y >= 0) || (x <= 0 && y <= 0)) {
            return x - y;
        }

        if (x > 0) {
            if (x > Integer.MAX_VALUE + y)
                throw new OverflowException();
        } else {
            if (x < Integer.MIN_VALUE + y)
                throw new UnderflowException();
        }
        return x - y;
    }
}
