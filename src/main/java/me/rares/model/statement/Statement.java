package me.rares.model.statement;

import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws InvalidVariableNameException;
    String toString();
}
