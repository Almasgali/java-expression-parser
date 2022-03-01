package expression;

import java.util.Objects;

public class UnaryMinus implements MyExpression {
    private final MyExpression exp;

    public UnaryMinus(MyExpression expression) {
        exp = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -exp.evaluate(x, y, z);
    }

    @Override
    public int evaluate(int x) {
        return -exp.evaluate(x);
    }

    @Override
    public String toString() {
        return "-(" + exp.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        UnaryMinus that = (UnaryMinus) o;
        return Objects.equals(exp, that.exp);
    }

    @Override
    public int hashCode() {
        return exp.hashCode() * 33;
    }
}
