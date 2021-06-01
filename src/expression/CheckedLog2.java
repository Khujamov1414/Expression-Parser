package expression;

import expression.exceptions.NumberNotIntegerException;

public class CheckedLog2 extends UnaryOperations {

    public CheckedLog2(CommonExpression expression) {
        super(expression);
    }

    @Override
    protected int operate(int x) {

        if (x <= 0) {
            throw new NumberNotIntegerException("Логарифма по основанию 2 от отрицательного числа или нуля не существует");
        }

        int ans = 0;
        while (x >= 2) {
            ans++;
            x /= 2;
        }
        return ans;
    }

    @Override
    protected String getOperator() {
        return "log2";
    }
}
