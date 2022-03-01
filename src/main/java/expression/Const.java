package expression;

public class Const implements MyExpression {

    private final int val;

    public Const(int val) {
        this.val = val;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return val;
    }

    @Override
    public int evaluate(int x) {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Const) {
            Const temp = (Const) obj;
            return this.toString().equals(temp.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return val * 17;
    }
}
