package expression.exceptions;

public class UnderflowException extends EvaluatingException {
    public UnderflowException() {
        super("Выход за нижнюю границу "); //переполнение снизу
    }
}
