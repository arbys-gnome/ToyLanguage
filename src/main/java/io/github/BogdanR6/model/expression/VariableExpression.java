package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.UndefinedVariableException;
import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.value.Value;

public record VariableExpression(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws UndefinedVariableException {
        return symbolTable.getValue(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
