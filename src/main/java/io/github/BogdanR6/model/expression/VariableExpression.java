package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.value.Value;

public record VariableExpression(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws InvalidVariableNameException {
        if(!symbolTable.isDefined(variableName))
            throw new InvalidVariableNameException("Variable " + variableName + " is not defined");

        return symbolTable.getValue(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
