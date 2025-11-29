package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.state.ProgramState;

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
