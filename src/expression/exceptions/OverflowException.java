package expression.exceptions;

public class OverflowException extends EvaluatingException {
    public OverflowException() {
        super("Выход за верхнюю границу"); //переполнение сверху
    }
}
