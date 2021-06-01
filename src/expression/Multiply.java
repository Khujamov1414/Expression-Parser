package expression;

public class Multiply extends BinaryOperations {

    public Multiply(CommonExpression leftSide, CommonExpression rightSide) {
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
        return x * y;
    }
}
