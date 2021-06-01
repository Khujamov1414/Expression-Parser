package expression;

public class Main {
    public static void main(String[] args) {
        CommonExpression expression = new CheckedLog2(new Const(-4));
        System.out.println(expression.toString() + " = " + expression.evaluate(2));

        expression = new CheckedPow2(new Const(-1));
        System.out.println(expression.toString() + " = " + expression.evaluate(2));
    }
}
