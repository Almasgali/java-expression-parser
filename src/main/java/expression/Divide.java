package expression;

public class Divide extends AbstractOperation {

    public Divide(MyExpression arg1, MyExpression arg2) {
        super(arg1, arg2, "/");
    }

    @Override
    protected int eval(int x, int y) {
        return x / y;
    }
}
