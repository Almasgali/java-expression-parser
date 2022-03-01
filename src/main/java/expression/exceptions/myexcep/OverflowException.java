package expression.exceptions.myexcep;

public class OverflowException extends RuntimeException {
    public OverflowException() {
        super("Error: overflow");
    }
}
