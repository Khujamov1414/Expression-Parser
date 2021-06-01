package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionParser implements Parser {
    private Map<String, Parts> myCharacters;
    private Map<String, Integer> priority;

    public ExpressionParser() {
        myCharacters = new HashMap<>();
        myCharacters.put("+", Parts.ADD);
        myCharacters.put("-", Parts.SUBTRACT);
        myCharacters.put("*", Parts.MULTIPLY);
        myCharacters.put("/", Parts.DIVIDE);
        myCharacters.put("log2", Parts.LOG2);
        myCharacters.put("pow2", Parts.POW2);
        myCharacters.put("x", Parts.X);
        myCharacters.put("y", Parts.Y);
        myCharacters.put("z", Parts.Z);
        myCharacters.put("(", Parts.LEFT_BRACKET);
        myCharacters.put(")", Parts.RIGHT_BRACKET);
        myCharacters.put("begin", Parts.BEGIN);
        myCharacters.put("number", Parts.NUMBER);

        priority = Map.of(
                "+", 0,
                "-", 0,
                ")", 1,
                "*", 1,
                "/", 1
        );
    }

    private void isUnexpectedCharacter(char ch, int pos) throws UnexpectedCharacter {

        if (!myCharacters.containsKey(String.valueOf(ch)) &&
                !isNumber(ch) &&
                !Character.isWhitespace(ch)
        ) {
            throw new UnexpectedCharacter(ch, pos);
        }
    }

    private void isOperatorException(String operator, int pos, Parts previous) throws IllegalOperatorException  {

        if (previous == Parts.ADD ||
                previous == Parts.SUBTRACT ||
                previous == Parts.MULTIPLY ||
                previous == Parts.DIVIDE ||
                previous == Parts.BEGIN ||
                previous == Parts.LEFT_BRACKET ||
                previous == Parts.LOG2 ||
                previous == Parts.POW2) {
            throw new IllegalOperatorException(operator, pos);
        }
    }

    private void isVariableException(String str, int pos, Parts previous) throws IllegalVariableException {

        if (previous == Parts.NUMBER ||
                previous == Parts.X ||
                previous == Parts.Y ||
                previous == Parts.Z ||
                previous == Parts.RIGHT_BRACKET) {
            throw new IllegalVariableException(str, pos);
        }
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private List<Pair<Parts, String>> analyzing(String expression) throws SyntaxException {
        expression += "    ";
        List<Pair<Parts, String>> list = new ArrayList<>();
        String previous = "begin";
        list.add(new Pair<>(myCharacters.get(previous), previous));
        StringBuilder sb = new StringBuilder();
        int leftBracket = 0;
        int rightBracket = 0;

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == ')')  {

                if (ch == ')') {
                    rightBracket++;
                }

                if (priority.get(String.valueOf(ch)) == 1) {
                    isOperatorException(String.valueOf(ch), i, myCharacters.get(previous));
                }

                if (ch == '-' &&
                        ((!previous.equals(")") && priority.containsKey(previous)) ||
                                previous.equals("(") ||
                                previous.equals("begin") ||
                                previous.equals("log2") ||
                                previous.equals("pow2"))) {
                    sb = new StringBuilder("-");
                }
                previous = String.valueOf(ch);
                list.add(new Pair<>(myCharacters.get(previous), previous));

            }  else if (ch == 'x' || ch == 'y' || ch == 'z' || ch == '(' || isNumber(ch)) {

                if (ch == '(') {
                    leftBracket++;
                }
                isVariableException(String.valueOf(ch), i, myCharacters.get(previous));

                if (isNumber(ch)) {

                    if (sb.toString().equals("-")) {
                        list.remove(list.size() - 1);
                    }

                    while (isNumber(ch)) {
                        sb.append(ch);
                        ch = expression.charAt(++i);
                    }
                    i--;
                    previous = "number";
                    list.add(new Pair<>(myCharacters.get(previous), sb.toString()));

                } else {
                    previous = String.valueOf(ch);
                    list.add(new Pair<>(myCharacters.get(previous), previous));
                }
                sb = new StringBuilder();

            } else if (ch == 'p' || ch == 'l') {
                String unary = String.valueOf(ch) + expression.charAt(i + 1) + expression.charAt(i + 2) + expression.charAt(i + 3);

                if ((unary.equals("log2") ||
                        unary.equals("pow2")) &&
                        !Character.isLetter(expression.charAt(i + 4))) {
                    isVariableException(String.valueOf(ch), i, myCharacters.get(previous));
                    i += 3;

                    if (unary.equals("log2")) {
                        previous = "log2";
                    } else {
                        previous = "pow2";
                    }
                    list.add(new Pair<>(myCharacters.get(previous), previous));
                } else {
                    isUnexpectedCharacter(ch, i);
                }
            } else {
                sb = new StringBuilder();
                isUnexpectedCharacter(ch, i);
            }

            if (leftBracket < rightBracket) {
                throw new IllegalBracketException("Закрывающая скобка без открывающей на позиции " + i);
            }
        }

        if (leftBracket > rightBracket) {
            throw new IllegalBracketException("Не хватает закрывающих скобок");
        }
        isOperatorException(list.get(list.size() - 1).getValue(), expression.length() - 1, myCharacters.get(previous));
        list.add(new Pair<>(Parts.END, "end"));
        return list;
    }

    @Override
    public TripleExpression parse(String expression) throws SyntaxException {
        //System.out.println(expression);
        BufferedParts bufferedParts = new BufferedParts(analyzing(expression));
        //System.out.println(bufferedParts.toString());
        return endExpression(bufferedParts);
    }

    private CommonExpression endExpression(BufferedParts bufferedParts) throws SyntaxException {
        Pair<Parts, String> pair = bufferedParts.getNext();

        if (pair.getKey() == Parts.END) {
            return new Const(0);
        } else {
            return AddSubtract(bufferedParts);
        }
    }

    private CommonExpression AddSubtract(BufferedParts bufferedParts) throws SyntaxException {
        CommonExpression value = MultiplyDivide(bufferedParts);

        while (true) {
            Pair<Parts, String> pair = bufferedParts.getNext();

            switch (pair.getKey()) {
                case ADD:
                    value = new CheckedAdd(value, MultiplyDivide(bufferedParts));
                    break;
                case SUBTRACT:
                    value = new CheckedSubtract(value, MultiplyDivide(bufferedParts));
                    break;
                default:
                    bufferedParts.goBack();
                    return value;
            }
        }
    }

    private CommonExpression MultiplyDivide(BufferedParts bufferedParts) throws SyntaxException {
        CommonExpression value = NumberBrackets(bufferedParts);

        while (true) {
            Pair<Parts, String> pair = bufferedParts.getNext();

            switch (pair.getKey()) {
                case MULTIPLY:
                    value = new CheckedMultiply(value, NumberBrackets(bufferedParts));
                    break;
                case DIVIDE:
                    value = new CheckedDivide(value, NumberBrackets(bufferedParts));
                    break;
                default:
                    bufferedParts.goBack();
                    return value;
            }
        }
    }

    private CommonExpression NumberBrackets(BufferedParts bufferedParts) throws SyntaxException {
        Pair<Parts, String> pair = bufferedParts.getNext();
        Pair<Parts, String> previous = bufferedParts.getPrevious();

        if ((previous.getKey() == Parts.LEFT_BRACKET ||
                previous.getKey() == Parts.ADD ||
                previous.getKey() == Parts.SUBTRACT ||
                previous.getKey() == Parts.DIVIDE ||
                previous.getKey() == Parts.BEGIN ||
                previous.getKey() == Parts.MULTIPLY ||
                previous.getKey() == Parts.LOG2 ||
                previous.getKey() == Parts.POW2) &&
                pair.getKey() == Parts.SUBTRACT) {
            return new CheckedNegate(NumberBrackets(bufferedParts));
        }

        switch (pair.getKey()) {
            case LOG2:
                return new CheckedLog2(NumberBrackets(bufferedParts));
            case POW2:
                return new CheckedPow2(NumberBrackets(bufferedParts));
            case NUMBER:
                try {
                    return new Const(Integer.parseInt(pair.getValue()));
                } catch (NumberFormatException e) {
                    throw new NumberNotIntegerException("Число не входит в целочисленный тип Integer");
                }
            case X:
                return new Variable("x");
            case Y:
                return new Variable("y");
            case Z:
                return new Variable("z");
            default:
                CommonExpression value = AddSubtract(bufferedParts);
                bufferedParts.getNext();
                return value;
        }

    }
}
