package io.github.arbys_gnome.model.operators;

import io.github.arbys_gnome.model.exception.InvalidOperatorException;

public enum ArithmeticOperator {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('*'),
    DIVIDE('/');

    private final char symbol;

    ArithmeticOperator(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static ArithmeticOperator fromChar(char symbol) throws InvalidOperatorException {
        for (ArithmeticOperator op : values()) {
            if (op.symbol == symbol) return op;
        }
        throw new InvalidOperatorException("Invalid operator: " + symbol);
    }
}
