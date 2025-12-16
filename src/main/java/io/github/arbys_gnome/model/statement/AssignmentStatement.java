package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

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
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        try {
            expression.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
