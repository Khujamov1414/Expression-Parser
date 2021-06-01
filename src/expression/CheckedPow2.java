package expression;

import expression.exceptions.NumberNotIntegerException;
import expression.exceptions.OverflowException;

public class CheckedPow2 extends UnaryOperations {
    public CheckedPow2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int operate(int x) {

        if (x < 0) {
            throw new NumberNotIntegerException("Степень является отрицательным");
        }
        if (x > 31) {
            throw new OverflowException();
        }
        return 1 << x;
    }

    @Override
    protected String getOperator() {
        return "pow2";
    }
}
