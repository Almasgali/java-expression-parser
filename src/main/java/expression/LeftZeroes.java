package expression;

import java.util.Objects;

public class LeftZeroes implements MyExpression {

    private final MyExpression expression;

    public LeftZeroes(MyExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (expression.evaluate(x, y, z) == 0) return 32;
        return 32 - Integer.toBinaryString(expression.evaluate(x, y, z)).length();
    }

    @Override
    public int evaluate(int x) {
        if (expression.evaluate(x) == 0) return 32;
        return 32 - Integer.toBinaryString(expression.evaluate(x)).length();
    }

    @Override
    public String toString() {
        return "l0(" + expression.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        LeftZeroes that = (LeftZeroes) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return expression.hashCode() * 517;
    }
}
