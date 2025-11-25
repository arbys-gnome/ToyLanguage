package me.rares.model.statement;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidTypeException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;

public record AssignmentStatement(String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        if (!state.symbolTable().isDefined(variableName)) {
            throw new InvalidVariableNameException(variableName + " not defined");
        }

        var value = expression.evaluate(state.symbolTable(), state.heap());
        var variableType = state.symbolTable().getType(variableName);
        var valueType = value.type();

        if (valueType != variableType) {
            throw new InvalidTypeException(valueType + " is not assignable to " + variableName + " of type " + variableType);
        }

        state.symbolTable().setValue(variableName, value);

        return state;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
