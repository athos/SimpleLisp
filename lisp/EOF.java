package lisp;

public class EOF extends LispObject {
    private static EOF instance = new EOF();

    public static EOF getInstance() {
	return instance;
    }

    public boolean isEOF() {
	return true;
    }
}
