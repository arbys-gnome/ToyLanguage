package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.statement.Statement;

public interface ExecutionStack extends Iterable<Statement> {
    void push(Statement statement);
    Statement pop();
    boolean isEmpty();
    String toString();

    void clear();
}
