package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.*;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.value.Value;

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
