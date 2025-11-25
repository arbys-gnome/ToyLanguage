package me.rares.model.expression;

import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.Value;

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
