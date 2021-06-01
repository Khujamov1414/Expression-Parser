package expression.exceptions;

public class IllegalOperatorException extends SyntaxException {
    public IllegalOperatorException(String operator, int pos) {
        super("Ожидали переменную, нашлось \"" + operator + "\" на позиции " + pos);
    }
}
