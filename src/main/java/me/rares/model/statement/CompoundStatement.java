package me.rares.model.statement;

import me.rares.model.state.ProgramState;

public record CompoundStatement(Statement first, Statement second) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        // Make sure 'first' executes first
        state.getExecutionStack().push(second);
        state.getExecutionStack().push(first);
        return state;
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString();
    }
}
