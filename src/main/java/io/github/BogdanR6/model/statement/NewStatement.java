package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.type.RefType;
import io.github.BogdanR6.model.value.RefValue;
import io.github.BogdanR6.model.value.Value;

public record NewStatement(String var, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        if (!state.symbolTable().isDefined(var)) {
            throw new InvalidVariableNameException(var + " is not defined.");
        }

        if (!state.symbolTable().getType(var).isReference()) {
            throw new InvalidTypeException(var + " is not reference.");
        }
        RefValue ref = (RefValue)state.symbolTable().getValue(var);
        Value value = expression.evaluate(state.symbolTable(), state.heap());
        if (!value.type().equals(((RefType)ref.type()).innerType())) {
            throw new InvalidTypeException("Can't assign a value of type " + value.type() + " to a reference of type " + ref.type() + ".");
        }

        int address = state.heap().allocate(value);

        state.symbolTable().setValue(var, new RefValue(address, value.type()));

        return state;
    }

    @Override
    public String toString() {
        return "new";
    }
}
