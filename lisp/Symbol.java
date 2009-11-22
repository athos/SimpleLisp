package lisp;

import java.util.*;

public class Symbol extends LispObject {
    private static Map<String, Symbol> symbolTable = new HashMap<String, Symbol>();
    private String symbolName;
    
    private Symbol(String symbolName) {
	this.symbolName = symbolName;
    }

    public boolean isSymbol() {
	return true;
    }
    
    public static Symbol getSymbol(String symbolName) {
	if (symbolTable.containsKey(symbolName)) {
	    return symbolTable.get(symbolName);
	}
	Symbol newSymbol = new Symbol(symbolName);
	symbolTable.put(symbolName, newSymbol);
	
	return newSymbol;
    }

    public String getSymbolName() {
	return symbolName;
    }

    public boolean equals(Symbol symbol) {
	return this == symbol;
    }

    public String toString() {
	return getSymbolName();
    }
}
