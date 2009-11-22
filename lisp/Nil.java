package lisp;

public class Nil extends LispObject implements List {
    private static Nil instance = new Nil();

    public static Nil getInstance() {
	return instance;
    }

    public boolean isNull() {
	return true;
    }

    public boolean isList() {
	return true;
    }

    public LispValue getFirst() {
	return instance;
    }

    public List getRest() {
	return instance;
    }

    public String toString() {
	return "()";
    }
}
