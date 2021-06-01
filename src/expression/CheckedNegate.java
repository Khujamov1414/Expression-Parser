package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperations {

    public CheckedNegate(CommonExpression value) {
        super(value);

    }

    @Override
    protected int operate(int x) {

        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -x;
    }

    @Override
    protected String getOperator() {
        return "-";
    }
}
