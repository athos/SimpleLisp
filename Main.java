package lisp;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
	lisp.Reader reader = new lisp.Reader(new InputStreamReader(System.in));
	Evaluator evaluator = new Evaluator();

	while (true) {
	    System.out.print("> ");
	    System.out.flush();

	    LispValue exp = reader.read();
	    if (exp.isEOF()) {
		break;
	    }
	    System.out.println(evaluator.eval(exp));
	}
	System.out.println();
    }
}