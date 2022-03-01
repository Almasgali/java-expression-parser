package expression.exceptions;

import expression.*;
import expression.exceptions.myexcep.*;

public class CheckedDivide extends AbstractOperation {

    public CheckedDivide(MyExpression arg1, MyExpression arg2) {
        super(arg1, arg2, "/");
    }

    private void check(int x, int y) {
        if (y == 0) {
            throw new DivideByZeroException();
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    @Override
    protected int eval(int x, int y) {
        check(x, y);
        return x / y;
    }
}