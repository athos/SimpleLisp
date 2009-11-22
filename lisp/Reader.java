package lisp;

import java.io.*;
import java.util.*;

public class Reader {
    private java.io.Reader in;
    private int ahead = 0;

    public Reader(java.io.Reader in) {
	this.in = new BufferedReader(in);
    }

    private char peekChar() throws IOException {
	if (ahead == 0) {
	    ahead = in.read();
	}
	return (char)ahead;
    }

    private char readChar() throws IOException {
	int ch;
	
	if (ahead != 0) {
	    ch = ahead;
	    ahead = 0;
	} else {
	    ch = in.read();
	}
	return (char)ch;
    }
    
    private char peekFirstNonspace() throws IOException {
	while (true) {
	    char ch = peekChar();

	    if (!Character.isWhitespace(ch)) {
		return ch;
	    }
	    readChar();
	    continue;
	}
    }

    public LispValue read() throws IOException {
	char ch = peekFirstNonspace();

	if (ch == (char)-1) {
	    return EOF.getInstance();
	} else if (Character.isDigit(ch)) {
	    return readNumber();
	} else if (isIdentifierStart(ch)) {
	    return readSymbol();
	} else if (ch == '(') {
	    return readList();
	} else if (ch == '#') {
	    return readBool();
	} else if (ch == '\'') {
	    readChar();
	    return createList(Symbol.getSymbol("quote"), read());
	} else if (ch == '`') {
	    readChar();
	    return createList(Symbol.getSymbol("quasiquote"), read());
	} else if (ch == ',') {
	    char ch1;
	    
	    readChar();
	    ch1 = peekChar();
	    if (ch1 == '@') {
		readChar();
		return createList(Symbol.getSymbol("unquote-splicing"), read());
	    } else {
		return createList(Symbol.getSymbol("unquote"), read());
	    }
	} else {
	    return Nil.getInstance();
	}
    }

    private boolean isIdentifierLetter(char ch) {
	return isIdentifierStart(ch)
	    || Character.isDigit(ch)
	    || ch == '.' || ch == '#';
    }
    
    private boolean isIdentifierStart(char ch) {
	return Character.isJavaIdentifierStart(ch)
	    || ch == '!' || ch == '%' || ch == '&'
	    || ch == '^' || ch == '-' || ch == '='
	    || ch == '@' || ch == '+' || ch == ':'
	    || ch == '*' || ch == '/' || ch == '_'
	    || ch == '<' || ch == '>' || ch == '?';
    }

    private List createList(LispValue v1, LispValue v2) {
	return new Cons(v1, new Cons(v2, Nil.getInstance()));
    }

    private Number readNumber() throws IOException {
	StringBuilder builder = new StringBuilder();

	for (char ch = peekChar(); Character.isDigit(ch); ch = peekChar()) {
	    builder.append(ch);
	    readChar();
	}
	String num = builder.toString();
	return Number.getNumber(Integer.parseInt(num));
    }

    private Symbol readSymbol() throws IOException {
	StringBuilder builder = new StringBuilder();

	for (char ch = peekChar(); isIdentifierLetter(ch); ch = peekChar()) {
	    builder.append(ch);
	    readChar();
	}
	return Symbol.getSymbol(builder.toString());
    }

    private List readList() throws IOException {
	readChar();
	List list = readListElements();
	readChar();
	
	return list;
    }

    private List readListElements()
	throws IOException {
	char ch = peekChar();

	if (ch == ')') {
	    return Nil.getInstance();
	}
	if (ch == '.') {
	    // FIXME
	    throw new IOException();
	}
	
	LispValue v = read();	

	if (peekFirstNonspace() == '.') {
	    readChar();
	    if (peekChar() == ')') {
		// FIXME
		throw new IOException();
	    }
	    LispValue v1 = read();
	    
	    if (peekFirstNonspace() != ')') {
		// FIXME
		throw new IOException();
	    }
	    return new Cons(v, v1);
	}
	return new Cons(v, readListElements());
    }

    private Bool readBool() throws IOException {
	readChar();

	char ch = peekChar();
	if (ch == 't') {
	    readChar();
	    return Bool.getInstance(true);
	} else if (ch == 'f') {
	    readChar();
	    return Bool.getInstance(false);
	} else {
	    // FIXME
	    return Bool.getInstance(false);
	}
    }
}
