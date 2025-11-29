package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.statement.Statement;

public interface ExecutionStack extends Iterable<Statement> {
    void push(Statement statement);
    Statement pop();
    boolean isEmpty();
    String toString();

    void clear();
}
