package me.rares.model.state;

import me.rares.model.statement.Statement;

public interface ExecutionStack {
    void push(Statement statement);
    Statement pop();
    boolean isEmpty();
    String toString();
}
