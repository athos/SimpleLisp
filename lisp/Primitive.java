package lisp;

public class Primitive extends LispObject implements Procedure {
    public boolean isProcedure() {
	return true;
    }
    
    public boolean isPrimitive() {
	return true;
    }

    public LispValue apply(List args) {
	return Nil.getInstance();
    }
}
