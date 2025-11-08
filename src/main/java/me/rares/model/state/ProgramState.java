package me.rares.model.state;

import me.rares.model.statement.Statement;

public record ProgramState(
    ExecutionStack executionStack,
    SymbolTable symbolTable,
    Output out
) {
    public boolean isFinished() {
        return executionStack.isEmpty();
    }

    public Statement nextStatement() {
        return executionStack.pop();
    }
}
