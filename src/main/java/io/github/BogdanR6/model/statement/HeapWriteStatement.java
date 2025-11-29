package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.InvalidHeapAddressException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.exception.InvalidVariableTypeException;
import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.value.RefValue;
import io.github.BogdanR6.model.value.Value;

public record HeapWriteStatement(String varName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws InvalidVariableNameException, InvalidVariableTypeException, InvalidHeapAddressException {
        if (!state.symbolTable().isDefined(varName)) {
            throw new InvalidVariableNameException(varName);
        }
        Value val = state.symbolTable().getValue(varName);
        if (!val.type().isReference()) {
            throw new InvalidVariableTypeException(varName + " is not a reference.");
        }
        Integer address = ((RefValue)val).address();
        if (!state.heap().isAllocated(address)) {
            throw new InvalidHeapAddressException(varName + " is not allocated.");
        }
        state.heap().write(address, val);
        return state;
    }

    @Override
    public String toString() {
        return "HeapWriteStatement";
    }
}
