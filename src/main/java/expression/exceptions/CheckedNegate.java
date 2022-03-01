package expression.exceptions;

import expression.*;
import expression.exceptions.myexcep.OverflowException;

public class CheckedNegate implements MyExpression {
    private final MyExpression exp;

    public CheckedNegate(MyExpression var1) {
        this.exp = var1;
    }

    public int evaluate(int var1, int var2, int var3) {
        check(var1, var2, var3);
        return -this.exp.evaluate(var1, var2, var3);
    }

    public int evaluate(int var1) {
        check(var1);
        return -this.exp.evaluate(var1);
    }

    private void check(int x) {
        if (exp.evaluate(x) == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    private void check(int x, int y, int z) {
        if (exp.evaluate(x, y, z) == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public String toString() {
        return "-(" + this.exp.toString() + ")";
    }

    public boolean equals(Object var1) {
        if (var1 instanceof CheckedNegate) {
            CheckedNegate var2 = (CheckedNegate) var1;
            return this.exp.equals(var2.exp);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.exp.hashCode() * 43;
    }
}
