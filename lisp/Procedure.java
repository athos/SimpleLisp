package lisp;

public interface Procedure extends LispValue {
    public boolean isPrimitive();
    public LispValue apply(List args);
}
