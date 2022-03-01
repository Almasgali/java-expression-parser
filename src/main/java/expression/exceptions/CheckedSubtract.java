package expression.exceptions;

import expression.*;
import expression.exceptions.myexcep.*;

public class CheckedSubtract extends AbstractOperation {

    public CheckedSubtract(MyExpression arg1, MyExpression arg2) {
        super(arg1, arg2, "-");
    }

    private void check(int x, int y) {
        if (x == 0 && y == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0) {
            if (x - Integer.MIN_VALUE < y) {
                throw new OverflowException();
            }
        }
        if (x > 0 && y < 0) {
            if (x - Integer.MAX_VALUE > y) {
                throw new OverflowException();
            }
        }
    }

    @Override
    protected int eval(int x, int y) {
        check(x, y);
        return x - y;
    }
}