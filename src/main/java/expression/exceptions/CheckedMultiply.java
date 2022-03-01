package expression.exceptions;

import expression.*;
import expression.exceptions.myexcep.*;

public class CheckedMultiply extends AbstractOperation {

    public CheckedMultiply(MyExpression arg1, MyExpression arg2) {
        super(arg1, arg2, "*");
    }

    private void check(int x, int y) {
        if (x > 0 && y < 0) {
            if (Integer.MIN_VALUE / x > y) {
                throw new OverflowException();
            }
        }
        if (x < 0 && y > 0) {
            if (Integer.MIN_VALUE / x > 0 && Integer.MIN_VALUE / x < y) {
                throw new OverflowException();
            }
        }
        if (x > 0 && y > 0) {
            if (Integer.MAX_VALUE / x < y) {
                throw new OverflowException();
            }
        }
        if (x < 0 && y < 0) {
            if (Integer.MAX_VALUE / x > y) {
                throw new OverflowException();
            }
        }
    }

    @Override
    protected int eval(int x, int y) {
        check(x, y);
        return x * y;
    }
}