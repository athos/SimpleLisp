package lisp;

public class Closure extends LispObject implements Procedure {
    private List args;
    private LispValue body;
    private Env env;

    public Closure(List args, LispValue body, Env env) {
	this.args = args;
	this.body = body;
	this.env = env;
    }
    
    public boolean isProcedure() {
	return true;
    }

    public boolean isPrimitive() {
	return false;
    }

    public LispValue apply(List args) {
	Evaluator evaluator = new Evaluator();

	return evaluator.eval(body, new Env(env).extend(this.args, args));
    }

    public String toString() {
	return "#<closure " + args + " " + body + ">";
    }
}
