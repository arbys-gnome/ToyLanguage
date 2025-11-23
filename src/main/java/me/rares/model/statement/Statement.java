package me.rares.model.statement;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.exception.InvalidVariableTypeException;
import me.rares.model.state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws InvalidVariableNameException, InvalidVariableTypeException, InvalidHeapAddressException;
    String toString();
}
