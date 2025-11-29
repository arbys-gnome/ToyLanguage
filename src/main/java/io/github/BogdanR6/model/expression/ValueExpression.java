package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.value.Value;

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
