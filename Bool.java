package lisp;

public class Bool extends LispObject {
    private static Bool trueValue = new Bool();
    private static Bool falseValue = new Bool();

    public static Bool getInstance(boolean bool) {
	if (bool) {
	    return trueValue;
	} else {
	    return falseValue;
	}
    }

    public boolean isFalse() {
	return this == falseValue;
    }

    public Bool negateValue() {
	if (isFalse()) {
	    return trueValue;
	} else {
	    return falseValue;
	}
    }

    public String toString() {
	if (this == trueValue) {
	    return "#t";
	} else {
	    return "#f";
	}
    }
}
