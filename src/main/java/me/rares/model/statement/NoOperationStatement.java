package me.rares.model.statement;

import me.rares.model.state.ProgramState;

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
