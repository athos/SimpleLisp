package lisp;

public abstract class LispObject implements LispValue {
    public boolean isNull() {
	return false;
    }

    public boolean isFalse() {
	return false;
    }

    public boolean isNumber() {
	return false;
    }

    public boolean isSymbol() {
	return false;
    }

    public boolean isBool() {
	return false;
    }

    public boolean isEOF() {
	return false;
    }

    public boolean isList() {
	return false;
    }

    public boolean isCons() {
	return false;
    }

    public boolean isProcedure() {
	return false;
    }
}