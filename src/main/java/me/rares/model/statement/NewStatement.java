package me.rares.model.statement;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.RefType;
import me.rares.model.value.RefValue;
import me.rares.model.value.Value;

public record NewStatement(String var, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws InvalidVariableNameException {
        if (!state.getSymbolTable().isDefined(var)) {
            throw new InvalidVariableNameException(var + " is not defined.");
        }

        if (!state.getSymbolTable().getType(var).isReference()) {
            throw new InvalidTypeException(var + " is not reference.");
        }
        RefValue ref = (RefValue)state.getSymbolTable().getValue(var);
        Value value = expression.evaluate(state.getSymbolTable());
        if (!value.type().equals(((RefType)ref.type()).innerType())) {
            throw new InvalidTypeException("Can't assign a value of type " + value.type() + " to a reference of type " + ref.type() + ".");
        }

        int address = state.getHeap().allocate(value);

        state.getSymbolTable().setValue(var, new RefValue(address, value.type()));

        return state;
    }

    @Override
    public String toString() {
        return "new";
    }
}
