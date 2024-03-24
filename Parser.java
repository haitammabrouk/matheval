import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private final List<Token> expression;

    public Parser(List<Token> expression) {
        this.expression = expression;
    }
    
    public List<Token> parse() {
        if (!isValidExpression()) {
            System.out.println("Invalid expression: " + expressionToString());
            return null;
        }

        List<Token> properlyParsedExpression = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < expression.size(); i++) {
            Type curr = expression.get(i).type();
            Type next = (i + 1 < expression.size()) ? expression.get(i + 1).type() : null;

            if (curr == Type.SUB && (next == Type.NUM || next == Type.LEFT_PAR)) {
                double value = parseNegativeValue(i);
                if (Double.isNaN(value)) {
                    System.out.println("Invalid negative value at position " + i);
                    return null;
                }
                properlyParsedExpression.add(new Token(Double.toString(value), Type.NUM));
                indexes.add(i + 1);
            } else {
                properlyParsedExpression.add(expression.get(i));
            }
        }

        if (indexes.size() > 0) {
            return properlyParsedExpression.stream()
                    .filter(token -> !indexes.contains(expression.indexOf(token)))
                    .collect(Collectors.toList());
        } else {
            return properlyParsedExpression;
        }
    }

    private double parseNegativeValue(int index) {
        double value = Double.NaN;
        try {
            value = -1 * Double.parseDouble(expression.get(index + 1).expression());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return value;
    }

    private boolean isValidExpression() {
        int parenthesesCount = 0;
        for (Token token : expression) {
            Type type = token.type();
            if (type == Type.LEFT_PAR) {
                parenthesesCount++;
            } else if (type == Type.RIGHT_PAR) {
                parenthesesCount--;
                if (parenthesesCount < 0) {
                    System.out.println("Unbalanced parentheses.");
                    return false;
                }
            } else if (!isValidTokenType(type)) {
                System.out.println("Invalid token type: " + type);
                return false;
            }
        }
        if (parenthesesCount != 0) {
            System.out.println("Unbalanced parentheses.");
            return false;
        }
        return true;
    }

    private boolean isValidTokenType(Type type) {
        return type == Type.ADD || type == Type.SUB || type == Type.MUL ||
               type == Type.DIV || type == Type.POW || type == Type.SQRT ||
               type == Type.SIN || type == Type.COS || type == Type.TAN ||
               type == Type.ASIN || type == Type.ACOS || type == Type.ATAN ||
               type == Type.LEFT_PAR || type == Type.RIGHT_PAR || type == Type.NUM;
    }

    private String expressionToString() {
        return expression.stream()
                .map(Token::expression)
                .collect(Collectors.joining(""));
    }
}
