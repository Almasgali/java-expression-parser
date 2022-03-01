package expression.exceptions.myexcep;

public class DivideByZeroException extends RuntimeException {
    public DivideByZeroException() {
        super("Error: division by zero");
    }
}
