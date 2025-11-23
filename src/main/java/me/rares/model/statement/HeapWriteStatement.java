package me.rares.model.statement;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.exception.InvalidVariableTypeException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.value.RefValue;
import me.rares.model.value.Value;

public record HeapWriteStatement(String varName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws InvalidVariableNameException, InvalidVariableTypeException, InvalidHeapAddressException {
        if (!state.getSymbolTable().isDefined(varName)) {
            throw new InvalidVariableNameException(varName);
        }
        Value val = state.getSymbolTable().getValue(varName);
        if (!val.type().isReference()) {
            throw new InvalidVariableTypeException(varName + " is not a reference.");
        }
        Integer address = ((RefValue)val).address();
        if (!state.getHeap().isAllocated(address)) {
            throw new InvalidHeapAddressException(varName + " is not allocated.");
        }
        state.getHeap().write(address, val);
        return state;
    }

    @Override
    public String toString() {
        return "HeapWriteStatement";
    }
}
