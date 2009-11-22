package lisp;

public class Evaluator {
    public LispValue eval(LispValue exp, Env env) {
	if (exp.isNull() || exp.isNumber() || exp.isBool()) {
	    return exp;
	}
	if (exp.isSymbol()) {
	    return env.getBindingValue((Symbol)exp);
	}
	if (isTaggedList(exp, Symbol.getSymbol("quote"))) {
	    return ((List)exp).getRest().getFirst();
	}
	if (isTaggedList(exp, Symbol.getSymbol("if"))) {
	    LispValue[] exps = ((List)exp).getRest().toArray();
	    LispValue testExp = exps[0];
	    LispValue thenExp = exps[1];
	    LispValue elseExp = exps[2];

	    if (!eval(testExp, env).isFalse()) {
		return eval(thenExp, env);
	    } else {
		return eval(elseExp, env);
	    }
	}
	if (isTaggedList(exp, Symbol.getSymbol("begin"))) {
	    List exps = ((List)exp).getRest();
	    LispValue val = Nil.getInstance();

	    for ( ; !exps.isNull(); exps = exps.getRest()) {
		LispValue e = exps.getFirst();
		val = eval(e, env);
	    }
	    return val;
	}
	if (isTaggedList(exp, Symbol.getSymbol("set!"))) {
	    LispValue[] exps = ((List)exp).getRest().toArray();
	    LispValue settee = exps[0];
	    LispValue e = exps[1];

	    env.setBindingValue((Symbol)settee, eval(e, env));
	    return Symbol.getSymbol("ok");
	}
	if (isTaggedList(exp, Symbol.getSymbol("define"))) {
	    List exps = ((List)exp).getRest();
	    LispValue definee = exps.getFirst();
	    List rest = exps.getRest();

	    LispValue n;
	    LispValue e;
	    if (definee.isList()) {
		n = ((List)definee).getFirst();
		e = new Cons(Symbol.getSymbol("lambda"),
			     new Cons(((List)definee).getRest(), rest));
	    } else {
		n = definee;
		e = rest.getFirst();
	    }
	    env.addBindingValue((Symbol)n, eval(e, env));
	    return Symbol.getSymbol("ok");
	}
	if (isTaggedList(exp, Symbol.getSymbol("lambda"))) {
	    LispValue[] exps = ((List)exp).getRest().toArray();
	    List args = (List)exps[0];
	    LispValue body = exps[1];

	    return new Closure(args, body, env);
	}
	if (exp.isCons()) {
	    Procedure proc = (Procedure)eval(((Cons)exp).getCar(), env);
	    List args = ((List)exp).getRest();
	    List vals = getEvaledArgs(args, env);
	    
	    return proc.apply(vals);
	}
	return exp;
    }

    private boolean isTaggedList(LispValue exp, Symbol symbol) {
	return exp.isCons() && ((Cons)exp).getCar() == symbol;
    }

    private List getEvaledArgs(List args, Env env) {
	List vals = Nil.getInstance();
	for ( ; !args.isNull(); args = args.getRest()) {
	    vals = new Cons(eval(args.getFirst(), env), vals);
	}
	return getReverseList(vals);
    }

    private List getReverseList(List vals) {
	List reversed = Nil.getInstance();
	for ( ; !vals.isNull(); vals = vals.getRest()) {
	    reversed = new Cons(vals.getFirst(), reversed);
	}
	return reversed;
    }
}
