package expression;

import java.lang.IllegalArgumentException;

public class Variable implements MyExpression {

    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x": {
                return x;
            }
            case "y": {
                return y;
            }
            case "z": {
                return z;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Variable) {
            Variable temp = (Variable) obj;
            return this.toString().equals(temp.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return var.hashCode() * 171;
    }
}
