package lisp;

import java.util.*;

public class Number extends LispObject {
    private static Map<Integer, Number> numberTable = new HashMap<Integer, Number>();
    private Integer number;

    private Number(Integer number) {
	this.number = number;
    }

    private Number(int number) {
	this(new Integer(number));
    }

    public boolean isNumber() {
	return true;
    }

    public static Number getNumber(Integer number) {
	if (numberTable.containsKey(number)) {
	    return numberTable.get(number);
	}
	Number newNumber = new Number(number);
	numberTable.put(number, newNumber);

	return newNumber;
    }

    public static Number getNumber(int number) {
	return getNumber(new Integer(number));
    }

    public Integer getValue() {
	return number;
    }

    public String toString() {
	return number.toString();
    }
}
