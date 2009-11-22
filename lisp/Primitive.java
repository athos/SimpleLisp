package lisp;

import java.util.*;

interface Implementation {
    LispValue compute(List args);
}


public class Primitive extends LispObject implements Procedure {
    private static Map<Symbol, Primitive> primitives;

    private Implementation impl;

    public Primitive(Implementation impl) {
	this.impl = impl;
    }
    
    public boolean isProcedure() {
	return true;
    }
    
    public boolean isPrimitive() {
	return true;
    }

    public LispValue apply(List args) {
	return impl.compute(args);
    }

    public String toString() {
	return "#<primitive>";
    }

    public static Map<Symbol, Primitive> getPrimitives() {
	if (primitives == null) {
	    installPrimitives();
	}
	return primitives;
    }

    private static void installPrimitives() {
	primitives = new HashMap<Symbol, Primitive>();

	put("+", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    Number n1 = (Number)as[0];
		    Number n2 = (Number)as[1];
		    
		    return Number.getNumber(n1.getValue() + n2.getValue());
		}});
	put("-", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    Number n1 = (Number)as[0];
		    Number n2 = (Number)as[1];
		    
		    return Number.getNumber(n1.getValue() - n2.getValue());
		}});
	put("*", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    Number n1 = (Number)as[0];
		    Number n2 = (Number)as[1];
		    
		    return Number.getNumber(n1.getValue() * n2.getValue());
		}});
	put("/", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    Number n1 = (Number)as[0];
		    Number n2 = (Number)as[1];
		    
		    return Number.getNumber(n1.getValue() / n2.getValue());
		}});
	put("=", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    Number n1 = (Number)as[0];
		    Number n2 = (Number)as[1];		    

		    if (n1.getValue() == n2.getValue()) {
			return Bool.getInstance(true);
		    } else {
			return Bool.getInstance(false);
		    }
		}});
	put("cons", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();

		    return new Cons(as[0], as[1]);
		}});
	put("null?", new Implementation() {
		public LispValue compute(List args) {
		    if (args.getFirst().isNull()) {
			return Bool.getInstance(true);
		    } else {
			return Bool.getInstance(false);
		    }
		}});
	put("car", new Implementation() {
		public LispValue compute(List args) {
		    return ((Cons)args.getFirst()).getCar();
		}});
	put("cdr", new Implementation() {
		public LispValue compute(List args) {
		    return ((Cons)args.getFirst()).getCdr();
		}});
	put("set-car!", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    ((Cons)as[0]).setCar(as[1]);

		    return Nil.getInstance();
		}});
	put("set-cdr!", new Implementation() {
		public LispValue compute(List args) {
		    LispValue[] as = args.toArray();
		    ((Cons)as[0]).setCdr(as[1]);

		    return Nil.getInstance();
		}});
    }

    private static void put(String name, Implementation impl) {
	primitives.put(Symbol.getSymbol(name), new Primitive(impl));
    }
}
