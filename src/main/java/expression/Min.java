package expression;

public class Min extends AbstractOperation {
    
    public Min(MyExpression arg1, MyExpression arg2) {
        super(arg1, arg2, "min");
    }
    @Override
    protected int eval(int x, int y) {
        return Math.min(x, y);
    }
}
