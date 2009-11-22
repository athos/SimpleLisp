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
	    List exps = ((List)exp).getRest();
	    LispValue testExp = exps.getFirst();
	    LispValue thenExp = exps.getRest().getFirst();
	    LispValue elseExp = exps.getRest().getRest().getFirst();

	    if (!eval(testExp, env).isFalse()) {
		return eval(thenExp, env);
	    } else {
		return eval(elseExp, env);
	    }
	}
	if (isTaggedList(exp, Symbol.getSymbol("set!"))) {
	    List exps = ((List)exp).getRest();
	    LispValue settee = exps.getFirst();
	    LispValue e = exps.getRest().getFirst();

	    env.setBindingValue((Symbol)settee, eval(e, env));
	    return Symbol.getSymbol("ok");
	}
	if (isTaggedList(exp, Symbol.getSymbol("define"))) {
	    List exps = ((List)exp).getRest();
	    LispValue definee = exps.getFirst();
	    LispValue e = exps.getRest().getFirst();

	    env.addBindingValue((Symbol)definee, eval(e, env));
	    return Symbol.getSymbol("ok");
	}
	return Nil.getInstance();
    }

    private boolean isTaggedList(LispValue exp, Symbol symbol) {
	return exp.isCons() && ((Cons)exp).getCar() == symbol;
    }
}
