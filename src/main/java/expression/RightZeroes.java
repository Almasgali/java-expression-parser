package expression;

import java.util.Objects;

public class RightZeroes implements MyExpression {

    private final MyExpression expression;

    public RightZeroes(MyExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (expression.evaluate(x, y, z) == 0) return 32;
        int val = expression.evaluate(x, y, z);
        int count = 0;
        while (val % 2 == 0 && val != 0) {
            val /= 2;
            count++;
        }
        return count;
    }

    @Override
    public int evaluate(int x) {
        if (expression.evaluate(x) == 0) return 32;
        int val = expression.evaluate(x);
        int count = 0;
        while (val % 2 == 0 && val != 0) {
            val /= 2;
            count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return "t0(" + expression.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        RightZeroes that = (RightZeroes) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return expression.hashCode() * 2739;
    }
}
