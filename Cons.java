package lisp;

public class Cons extends LispObject implements List {
    private LispValue car;
    private LispValue cdr;

    public Cons(LispValue car, LispValue cdr) {
	this.car = car;
	this.cdr = cdr;
    }

    public boolean isCons() {
	return true;
    }

    public boolean isList() {
	return true;
    }

    public LispValue getCar() {
	return car;
    }

    public LispValue getCdr() {
	return cdr;
    }

    public void setCar(LispValue newValue) {
	car = newValue;
    }

    public void setCdr(LispValue newValue) {
	cdr = newValue;
    }

    public boolean isNull() {
	return false;
    }

    public LispValue getFirst() {
	return getCar();
    }

    public List getRest() {
	return (List)getCdr();
    }

    public String toString() {
	StringBuilder builder = new StringBuilder();

	Cons cons = this;
	builder.append("(");
	while (true) {
	    builder.append(cons.getCar());
	    LispValue cdr = cons.getCdr();
	    if (cdr.isNull()) {
		break;
	    }
	    if (!cdr.isList()) {
		builder.append(" . ");
		builder.append(cdr);
		break;
	    }
	    builder.append(" ");
	    cons = (Cons)cdr;
	}
	builder.append(")");

	return builder.toString();
    }
}