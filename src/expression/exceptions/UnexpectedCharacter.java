package expression.exceptions;

public class UnexpectedCharacter extends SyntaxException {
    public UnexpectedCharacter(char c, int pos) {
        super("Неопознанный символ \'" + c + "\' на позиции " + pos + " ");
    }
}
