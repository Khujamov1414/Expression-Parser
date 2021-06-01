package expression;

public class Add extends BinaryOperations {

    public Add(CommonExpression leftSide, CommonExpression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    protected int priority() {
        return 0;
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    protected int operate(int x, int y) {
        return x + y;
    }
}
