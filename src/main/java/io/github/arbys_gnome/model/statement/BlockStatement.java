package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.state.ProgramState;

import java.util.List;

public record BlockStatement(List<Statement> statements) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        // A block executes statements in order.
        // So they must be pushed in REVERSE order.
        for (int i = statements.size() - 1; i >= 0; i--) {
            state.executionStack().push(statements.get(i));
        }

        return state;
    }

    @Override
    public String toString() {
        return statements.stream()
                .map(Object::toString)
                .reduce("", (acc, s) -> acc + s + "; ");
    }
}
