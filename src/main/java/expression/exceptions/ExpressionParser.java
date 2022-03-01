package expression.exceptions;

import java.util.*;
import expression.*;
import expression.exceptions.myexcep.*;

public class ExpressionParser implements Parser {

    private static final Map<Character, Token> TOKENS = Map.of(
            '+', Token.ADD,
            '-', Token.SUB,
            '*', Token.MUL,
            '/', Token.DIV,
            '(', Token.L_BR,
            ')', Token.R_BR,
            (char) 0, Token.END);

    private static class Pair {

        private final Token type;
        private final String value;

        public Pair(Token type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public Token getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return "[" + this.type + ", " + this.value + "]";
        }
    }

    private static class TokenBuffer {

        private final List<Pair> tokens;
        private final int len;
        private final Pair eof = new Pair(Token.END, " ");
        private int pos = 0;

        public TokenBuffer(List<Pair> tokens) {
            this.tokens = tokens;
            len = tokens.size();

        }

        public Pair peek() {
            if (pos < len) {
                return tokens.get(pos);
            }
            return eof;
        }

        public Pair get() {
            if (pos < len) {
                return tokens.get(pos++);
            }
            return eof;
        }

        public int getPos() {
            return pos;
        }
    }

    public MyExpression parse(String expression) {
        TokenBuffer tokens = new TokenBuffer(splitToTokens(expression));
        return minMax(tokens);
    }

    private static MyExpression prim(TokenBuffer tokens) {
        Pair pair = tokens.get();
        switch (pair.getType()) {
            case NUM: {
                return new Const(Integer.parseInt(pair.getValue()));
            }
            case VAR: {
                return new Variable(pair.getValue());
            }
            case UN_SUB: {
                return new CheckedNegate(prim(tokens));
            }
            case LZ: {
                return new LeftZeroes(prim(tokens));
            }
            case RZ: {
                return new RightZeroes(prim(tokens));
            }
            case L_BR: {
                MyExpression res = minMax(tokens);
                if (tokens.get().getType() != Token.R_BR) {
                    throw new InvalidTokenException("error: expected ')' on pos " + tokens.getPos());
                }
                return res;
            }
            default: {
                throw new InvalidTokenException("error: unexpected token on pos " + tokens.getPos());
            }
        }
    }

    private static MyExpression term(TokenBuffer tokens) {
        MyExpression res = prim(tokens);
        while (true) {
            switch (tokens.peek().getType()) {
                case MUL: {
                    tokens.get();
                    res = new CheckedMultiply(res, prim(tokens));
                    break;
                }
                case DIV: {
                    tokens.get();
                    res = new CheckedDivide(res, prim(tokens));
                    break;
                }
                case R_BR:
                case END:
                case ADD:
                case MIN:
                case MAX:
                case SUB: {
                    return res;
                }
                default: {
                    throw new InvalidTokenException("error: unexpected token on pos " + tokens.getPos());
                }
            }
        }
    }

    private static MyExpression expr(TokenBuffer tokens) {
        MyExpression res = term(tokens);
        while (true) {
            switch (tokens.peek().getType()) {
                case ADD: {
                    tokens.get();
                    res = new CheckedAdd(res, term(tokens));
                    break;
                }
                case SUB: {
                    tokens.get();
                    res = new CheckedSubtract(res, term(tokens));
                    break;
                }
                case MIN:
                case MAX:
                case END:
                case R_BR: {
                    return res;
                }
                default: {
                    throw new InvalidTokenException("error: unexpected token on pos " + tokens.getPos());
                }
            }
        }
    }

    private static MyExpression minMax(TokenBuffer tokens) {
        MyExpression res = expr(tokens);
        while (true) {
            switch (tokens.peek().getType()) {
                case MIN: {
                    tokens.get();
                    res = new Min(res, expr(tokens));
                    break;
                }
                case MAX: {
                    tokens.get();
                    res = new Max(res, expr(tokens));
                    break;
                }
                case END:
                case R_BR: {
                    return res;
                }
                default: {
                    throw new InvalidTokenException("error: unexpected token on pos " + tokens.getPos());
                }
            }
        }
    }

    private static List<Pair> splitToTokens(String expression) {
        List<Pair> tokens = new ArrayList<>();
        StringBuilder num = new StringBuilder();
        int balance = 0;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch >= '0' && ch <= '9') {
                num.append(ch);
                continue;
            } else {
                if (!(num.length() == 0)) {
                    if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).getType() == Token.UN_SUB) {
                        tokens.remove(tokens.size() - 1);
                        tokens.add(new Pair(Token.NUM, "-" + num));
                    } else {
                        tokens.add(new Pair(Token.NUM, num.toString()));
                    }
                    num = new StringBuilder();
                }
            }
            if (ch == 'x' || ch == 'y' || ch == 'z') {
                tokens.add(new Pair(Token.VAR, Character.toString(ch)));
            } else if (ch == 'm') {
                if (i < expression.length() - 3 && (Character.isWhitespace(expression.charAt(i + 3))
                        || expression.charAt(i + 3) == '-' || expression.charAt(i + 3) == '(')
                        && i > 0 && (Character.isWhitespace(expression.charAt(i - 1))
                        || expression.charAt(i - 1) == ')')) {
                    if (expression.charAt(i + 1) == 'i' && expression.charAt(i + 2) == 'n') {
                        tokens.add(new Pair(Token.MIN, "min"));
                        i += 2;
                    } else if (expression.charAt(i + 1) == 'a' && expression.charAt(i + 2) == 'x') {
                        tokens.add(new Pair(Token.MAX, "max"));
                        i += 2;
                    } else {
                        throw new InvalidTokenException("Error: expected min / max starting from pos: " + i);
                    }
                } else {
                    throw new InvalidTokenException("Error: unexpected token '" + ch + "' on pos: " + i);
                }
            } else if (ch == 't' || ch == 'l') {
                if (i < expression.length() - 2 && expression.charAt(i + 1) == '0'
                && (Character.isWhitespace(expression.charAt(i + 2)) 
                || expression.charAt(i + 2) == '-'
                || expression.charAt(i + 2) == '(')) {
                    tokens.add(new Pair(ch == 'l' ? Token.LZ : Token.RZ, ch + "0"));
                    i++;
                } else {
                    throw new InvalidTokenException("Error: expected " + ch + "0 starting from pos: " + i);
                }
            } else if (TOKENS.containsKey(ch)) {
                if (ch == '-') {
                    if (tokens.isEmpty() || (tokens.get(tokens.size() - 1).getType() != Token.NUM
                            && tokens.get(tokens.size() - 1).getType() != Token.R_BR
                            && tokens.get(tokens.size() - 1).getType() != Token.VAR)) {
                        tokens.add(new Pair(Token.UN_SUB, Character.toString(ch)));
                    } else {
                        tokens.add(new Pair(Token.SUB, Character.toString(ch)));
                    }
                } else {
                    if (ch == '(') {
                        balance++;
                    }
                    if (ch == ')') {
                        if (--balance < 0) {
                            throw new InvalidTokenException("Unexpected ')' while parsing on pos: " + i);
                        }
                    }
                    tokens.add(new Pair(TOKENS.get(ch), Character.toString(ch)));
                }
            } else if (!Character.isWhitespace(ch)) {
                throw new InvalidTokenException("Unexpected symbol while parsing: '" + ch + "', on pos: " + i);
            }
        }
        if (!(num.length() == 0)) {
            if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).getType() == Token.UN_SUB) {
                tokens.remove(tokens.size() - 1);
                tokens.add(new Pair(Token.NUM, "-" + num));
            } else {
                tokens.add(new Pair(Token.NUM, num.toString()));
            }
        }
        if (balance != 0) {
            String missed = balance < 0 ? " '('." : " ')'.";
            throw new InvalidTokenException("Error: expression missed " + balance + missed);
        }
        return tokens;
    }
}