package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.state.ProgramState;

public class NoOperationStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }

    @Override
    public String toString() {
        return "NoOp";
    }
}
