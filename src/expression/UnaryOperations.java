package expression;

import expression.exceptions.NumberNotIntegerException;

public  abstract class UnaryOperations implements CommonExpression {
    private CommonExpression expression;

    public UnaryOperations(CommonExpression expression) {
        this.expression = expression;
    }

    protected abstract int operate(int x) throws NumberNotIntegerException;
    protected abstract String getOperator();

    @Override
    public int evaluate(int x) {
        return operate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operate(expression.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return getOperator() + "(" + expression.toString() + ")";
    }
}
