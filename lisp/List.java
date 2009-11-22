package lisp;

public interface List extends LispValue {
    public boolean isNull();
    public LispValue getFirst();
    public List getRest();
    public Number getLength();
    public LispValue[] toArray();
}
