package expression;

public class Subtract extends BinaryOperations {

    public Subtract(CommonExpression leftSide, CommonExpression rightSide) {
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
    protected int operate(int x, int y) {
        return x - y;
    }
}
