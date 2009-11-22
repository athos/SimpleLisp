import lisp.*;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
	lisp.Reader reader = new lisp.Reader(new InputStreamReader(System.in));
	Evaluator evaluator = new Evaluator();
	Env env = new Env();

	installPrimitives(env);
	while (true) {
	    System.out.print("> ");
	    System.out.flush();

	    LispValue exp = reader.read();
	    if (exp.isEOF()) {
		break;
	    }
	    System.out.println(evaluator.eval(exp, env));
	}
	System.out.println();
    }

    private static void installPrimitives(Env env) {
	Map<Symbol, Primitive> primitives = Primitive.getPrimitives();

	for (Map.Entry<Symbol, Primitive> entry : primitives.entrySet()) {
	    env.addBindingValue(entry.getKey(), entry.getValue());
	}
    }
}