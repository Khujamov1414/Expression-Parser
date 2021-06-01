package expression;

public class Divide extends BinaryOperations {

    public Divide(CommonExpression leftSide, CommonExpression rightSide) {
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
        return x / y;
    }
}
