import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scanner {

    private final String expression;

    public Scanner(String expression) {
        this.expression = expression;
    }

    public List<Token> scan() {
        StringBuilder valueBuilder = new StringBuilder();
        List<Token> scannedTokens = new ArrayList<>();
        
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            
            if (Character.isWhitespace(currentChar)) {
                continue;
            }
            
            Type type;
            switch (currentChar) {
                case '+':
                    type = Type.ADD;
                    break;
                case '-':
                    type = Type.SUB;
                    break;
                case '*':
                    type = Type.MUL;
                    break;
                case '/':
                    type = Type.DIV;
                    break;
                case '^':
                    type = Type.POW;
                    break;
                case '(':
                    type = Type.LEFT_PAR;
                    break;
                case ')':
                    type = Type.RIGHT_PAR;
                    break;
                default:
                    if (Character.isDigit(currentChar) || currentChar == '.') {
                        valueBuilder.append(currentChar);
                        continue;
                    } else if (Character.isLetter(currentChar)) {
                        if (expression.regionMatches(i, "sqrt", 0, 4)) {
                            type = Type.SQRT;
                            i += 3;
                        } else if (expression.regionMatches(i, "sin", 0, 3)) {
                            type = Type.SIN;
                            i += 2;
                        } else if (expression.regionMatches(i, "cos", 0, 3)) {
                            type = Type.COS;
                            i += 2;
                        } else if (expression.regionMatches(i, "tan", 0, 3)) {
                            type = Type.TAN;
                            i += 2;
                        } else if (expression.regionMatches(i, "asin", 0, 4)) {
                            type = Type.ASIN;
                            i += 3;
                        } else if (expression.regionMatches(i, "acos", 0, 4)) {
                            type = Type.ACOS;
                            i += 3;
                        } else if (expression.regionMatches(i, "atan", 0, 4)) {
                            type = Type.ATAN;
                            i += 3;
                        } else {
                            type = Type.NUM;
                            valueBuilder.append(currentChar);
                            continue;
                        }
                    } else {
                        type = Type.NUM;
                        valueBuilder.append(currentChar);
                        continue;
                    }
            }
            
            if (valueBuilder.length() > 0) {
                scannedTokens.add(new Token(valueBuilder.toString(), Type.NUM));
                valueBuilder.setLength(0); 
            }
            
            scannedTokens.add(new Token(String.valueOf(currentChar), type));
        }
        
        if (valueBuilder.length() > 0) {
            scannedTokens.add(new Token(valueBuilder.toString(), Type.NUM));
        }
        
        return scannedTokens;
    }

    public double evalSimpleExpr(List<Token> expression) {
        if (expression.size() == 1) {
            return Double.parseDouble(expression.get(0).expression());
        } else {
            int powIdx = expression.stream()
                    .map(Token::type)
                    .collect(Collectors.toList())
                    .indexOf(Type.POW);
    
            if (powIdx != -1) {
                double base = Double.parseDouble(expression.get(powIdx - 1).expression());
                double exp = Double.parseDouble(expression.get(powIdx + 1).expression());
                double ans = Math.pow(base, exp);
    
                expression.remove(powIdx - 1);
                expression.remove(powIdx - 1);
                expression.set(powIdx - 1, new Token(Double.toString(ans), Type.NUM));
                    return evalSimpleExpr(expression);
            }
                int sqrtIdx = expression.stream()
                    .map(Token::type)
                    .collect(Collectors.toList())
                    .indexOf(Type.SQRT);
    
            if (sqrtIdx != -1) {
                double value = Double.parseDouble(expression.get(sqrtIdx + 1).expression());
                double ans = Math.sqrt(value);
                    expression.remove(sqrtIdx); 
                expression.remove(sqrtIdx); 
                expression.add(sqrtIdx, new Token(Double.toString(ans), Type.NUM));
    
                return evalSimpleExpr(expression);
            }
            Optional<Type> trigonometricOptional = expression.stream()
                    .map(Token::type)
                    .filter(type -> type == Type.COS || type == Type.SIN || type == Type.TAN
                            || type == Type.ACOS || type == Type.ASIN || type == Type.ATAN)
                    .findFirst();
    
            if (trigonometricOptional.isPresent()) {
                Type trigonometricType = trigonometricOptional.get();
                int trigonometricIdx = IntStream.range(0, expression.size())
                        .filter(i -> expression.get(i).type() == trigonometricType)
                        .findFirst()
                        .orElse(-1);
                if (trigonometricIdx != -1) {
                    double value = evaluateTrigonometricExpression(expression, trigonometricIdx);
                    expression.set(trigonometricIdx, new Token(Double.toString(value), Type.NUM));
                    expression.remove(trigonometricIdx + 1);
                    return evalSimpleExpr(expression);
                }
            }
    
            for (Type operator : List.of(Type.ADD, Type.SUB, Type.MUL, Type.DIV)) {
                int operatorIdx = expression.stream()
                        .map(Token::type)
                        .collect(Collectors.toList())
                        .indexOf(operator);
    
                if (operatorIdx != -1) {
                    double leftOperand = evalSimpleExpr(expression.subList(0, operatorIdx));
                    double rightOperand = evalSimpleExpr(expression.subList(operatorIdx + 1, expression.size()));
                    switch (operator) {
                        case ADD:
                            return leftOperand + rightOperand;
                        case SUB:
                            return leftOperand - rightOperand;
                        case MUL:
                            return leftOperand * rightOperand;
                        case DIV:
                            return leftOperand / rightOperand;
                    }
                }
            }
            return -1;
        }
    }
    

    public double eval(List<Token> tokenizedExpression) {

        if (tokenizedExpression.size() == 1) {
            return Double.parseDouble(tokenizedExpression.get(0).expression());
        }
        List<Token> simpleExpr = new ArrayList<>();

        int idx =
            tokenizedExpression.stream()
                .map(Token::type)
                .collect(Collectors.toList())
                .lastIndexOf(Type.LEFT_PAR);
        int matchingRIGHT_PAR = -1;
        if (idx >= 0) {
            for (int i = idx + 1; i < tokenizedExpression.size(); i++) {
                Token curr = tokenizedExpression.get(i);
                if (curr.type() == Type.RIGHT_PAR) {
                    matchingRIGHT_PAR = i;
                    break;
                } else {
                    simpleExpr.add(tokenizedExpression.get(i));
                }
            }
        } else {
            simpleExpr.addAll(tokenizedExpression);
            return evalSimpleExpr(tokenizedExpression);
        }

        double value = evalSimpleExpr(simpleExpr);

        List<Token> partiallyEvaluatedExpression = new ArrayList<>();
        for (int i = 0; i < idx; i++) {
            partiallyEvaluatedExpression.add(tokenizedExpression.get(i));
        }
        partiallyEvaluatedExpression.add(new Token(Double.toString(value), Type.NUM));
        for (int i = matchingRIGHT_PAR + 1; i < tokenizedExpression.size(); i++) {
            partiallyEvaluatedExpression.add(tokenizedExpression.get(i));
        }

        System.out.println(partiallyEvaluatedExpression);
        return eval(partiallyEvaluatedExpression);
    }

    private double evaluateTrigonometricExpression(List<Token> expression, int idx) {
        Type trigonometricFunction = expression.get(idx).type();
        double argument = Double.parseDouble(expression.get(idx + 1).expression());
    
        switch (trigonometricFunction) {
            case COS:
                return Math.cos(argument);
            case SIN:
                return Math.sin(argument);
            case TAN:
                return Math.tan(argument);
            case ACOS:
                return Math.acos(argument);
            case ASIN:
                return Math.asin(argument);
            case ATAN:
                return Math.atan(argument);
            default:
                return Double.NaN;
        }
    }
}
