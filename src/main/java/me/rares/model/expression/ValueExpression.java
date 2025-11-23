package me.rares.model.expression;

import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.Value;

public record ValueExpression(Value value) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
