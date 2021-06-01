package expression.exceptions;

public class DivideByZeroException extends EvaluatingException {
    public DivideByZeroException() {
        super("Происходит деление на ноль");
    }
}
