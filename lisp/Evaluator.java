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
	if (isTaggedList(exp, Symbol.getSymbol("set!"))) {
	    LispValue[] exps = ((List)exp).getRest().toArray();
	    LispValue settee = exps[0];
	    LispValue e = exps[1];

	    env.setBindingValue((Symbol)settee, eval(e, env));
	    return Symbol.getSymbol("ok");
	}
	if (isTaggedList(exp, Symbol.getSymbol("define"))) {
	    LispValue[] exps = ((List)exp).getRest().toArray();
	    LispValue definee = exps[0];
	    LispValue e = exps[1];

	    env.addBindingValue((Symbol)definee, eval(e, env));
	    return Symbol.getSymbol("ok");
	}
	return Nil.getInstance();
    }

    private boolean isTaggedList(LispValue exp, Symbol symbol) {
	return exp.isCons() && ((Cons)exp).getCar() == symbol;
    }
}
