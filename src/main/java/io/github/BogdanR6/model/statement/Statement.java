package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws Exception;
    String toString();
}
