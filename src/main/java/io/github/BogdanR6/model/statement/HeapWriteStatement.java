package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.*;
import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.value.RefValue;
import io.github.BogdanR6.model.value.Value;

public record HeapWriteStatement(String varName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws UndefinedVariableException, InvalidDereferencingException, UnallocatedAddressException {
        Value val = state.symbolTable().getValue(varName);
        Integer address = val.address();
        state.heap().write(address, val);
        return state;
    }

    @Override
    public String toString() {
        return "HeapWriteStatement";
    }
}
