package expression.parser;

import expression.*;
import expression.exceptions.SyntaxException;

import java.util.Scanner;

public class MainForParser {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            String expression = "log2x";
            CommonExpression expparse = (CommonExpression) new ExpressionParser().parse(expression);

            System.out.println(expparse.toString());
            System.out.println(expparse.evaluate(1, 0, 0));
        } catch(SyntaxException e) {
            System.out.println(e.getMessage());
        }
    }
}
//276463616