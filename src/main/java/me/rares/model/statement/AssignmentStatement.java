package me.rares.model.statement;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.state.SymbolTable;

public record AssignmentStatement(String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws InvalidVariableNameException {
        SymbolTable symbolTable = state.getSymbolTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new InvalidVariableNameException(variableName + " not defined");
        }

        var value = expression.evaluate(symbolTable);
        var variableType = symbolTable.getType(variableName);
        var valueType = value.getType();

        if (valueType != variableType) {
            throw new InvalidTypeException(valueType + " is not assignable to " + variableName + " of type " + variableType);
        }

        symbolTable.setValue(variableName, value);

        return state;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
