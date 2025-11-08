package me.rares.model.expression;

import me.rares.model.state.SymbolTable;
import me.rares.model.value.Value;

public record VariableExpression(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        if(!symbolTable.isDefined(variableName))
            throw new RuntimeException("Variable " + variableName + " is not defined");

        return symbolTable.getValue(variableName);
    }
}
