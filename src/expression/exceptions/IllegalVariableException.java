package expression.exceptions;

public class IllegalVariableException extends SyntaxException {
    public IllegalVariableException(String val, int pos) {
        super("Ожидали операцию, нашлось \"" + val + "\" на позиции " + pos);
    }
}
