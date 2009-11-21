package lisp;

public interface LispValue {
    public boolean isNull();
    public boolean isFalse();
    public boolean isNumber();
    public boolean isSymbol();
    public boolean isList();
    public boolean isCons();
}
