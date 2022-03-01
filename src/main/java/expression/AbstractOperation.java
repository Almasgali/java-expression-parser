package expression;

import java.util.Objects;

public abstract class AbstractOperation implements MyExpression {

    private final MyExpression arg1;
    private final MyExpression arg2;
    private final String operator;

    public AbstractOperation(MyExpression arg1, MyExpression arg2, String operator) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.operator = operator;
    }

    public int evaluate(int x) {
        return eval(arg1.evaluate(x), arg2.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return eval(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
    }

    protected abstract int eval(int x, int y);

    @Override
    public String toString() {
        return "(" + arg1.toString() + " " + operator + " " + arg2.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractOperation that = (AbstractOperation) o;
        return Objects.equals(arg1, that.arg1) && Objects.equals(arg2, that.arg2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, operator);
    }
}
