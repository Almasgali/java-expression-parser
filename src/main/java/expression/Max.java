package expression;

public class Max extends AbstractOperation {
    
    public Max(MyExpression arg1, MyExpression arg2) {
        super(arg1, arg2, "max");
    }
    @Override
    protected int eval(int x, int y) {
        return Math.max(x, y);
    }
}
