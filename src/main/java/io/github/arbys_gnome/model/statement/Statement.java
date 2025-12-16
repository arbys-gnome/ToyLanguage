package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws Exception;
    String toString();
}
