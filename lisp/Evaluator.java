package lisp;

public class Evaluator {
    public LispValue eval(LispValue exp) {
	if (exp.isNull() || exp.isNumber() || exp.isBool()) {
	    return exp;
	}
	if (isTaggedList(exp, Symbol.getSymbol("quote"))) {
	    return ((List)exp).getRest().getFirst();
	}
	if (isTaggedList(exp, Symbol.getSymbol("if"))) {
	    List exps = ((List)exp).getRest();
	    LispValue testExp = exps.getFirst();
	    LispValue thenExp = exps.getRest().getFirst();
	    LispValue elseExp = exps.getRest().getRest().getFirst();

	    if (!eval(testExp).isFalse()) {
		return eval(thenExp);
	    } else {
		return eval(elseExp);
	    }
	}
	return Nil.getInstance();
    }

    private boolean isTaggedList(LispValue exp, Symbol symbol) {
	return exp.isCons() && ((Cons)exp).getCar() == symbol;
    }
}
