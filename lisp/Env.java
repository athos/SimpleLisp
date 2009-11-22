package lisp;

import java.util.*;

public class Env {
    class Frame {
	Map<Symbol, LispValue> bindings;

	Frame() {
	    bindings = new HashMap<Symbol, LispValue>();
	}

	boolean hasBinding(Symbol sym) {
	    return bindings.containsKey(sym);
	}
	
	LispValue getBindingValue(Symbol sym) {
	    return bindings.get(sym);
	}

	void addBinding(Symbol sym, LispValue val) {
	    bindings.put(sym, val);
	}

	void updateBinding(Symbol sym, LispValue val) {
	    bindings.put(sym, val);
	}
    }

    Frame frame;
    Env env;

    public Env() {
	this(null);
    }
    
    public Env(Env enclosingEnv) {
	frame = new Frame();
	env = enclosingEnv;
    }

    public Frame getFirstFrame() {
	return frame;
    }

    public Env getEnclosingEnvironment() {
	return env;
    }

    public Env extend(List vars, List vals) {
	while (!vars.isNull() && !vals.isNull()) {
	    Symbol sym = (Symbol)((Cons)vars).getCar();
	    LispValue val = ((Cons)vals).getCar();

	    frame.addBinding(sym, val);

	    vars = vars.getRest();
	    vals = vals.getRest();
	}
	return this;
    }

    public LispValue getBindingValue(Symbol sym) {
	Env env = this;

	while (env != null) {
	    Frame frame = env.getFirstFrame();

	    if (frame.hasBinding(sym)) {
		return frame.getBindingValue(sym);
	    }
	    env = env.getEnclosingEnvironment();
	}
	// FIXME
	return Bool.getInstance(false);
    }

    public void setBindingValue(Symbol sym, LispValue val) {
	Env env = this;

	while (env != null) {
	    Frame frame = env.getFirstFrame();

	    if (frame.hasBinding(sym)) {
		frame.updateBinding(sym, val);
		return;
	    }
	    env = env.getEnclosingEnvironment();
	}
	// FIXME
    }

    public void addBindingValue(Symbol sym, LispValue val) {
	Frame frame = this.frame;

	if (frame.hasBinding(sym)) {
	    frame.updateBinding(sym, val);
	} else {
	    frame.addBinding(sym, val);
	}
    }
}