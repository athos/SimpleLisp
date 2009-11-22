package lisp;

public interface List extends LispValue {
    public boolean isNull();
    public LispValue getFirst();
    public List getRest();
}
