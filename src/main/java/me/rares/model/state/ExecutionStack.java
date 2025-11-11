package me.rares.model.state;

import me.rares.model.statement.Statement;

import java.util.Iterator;

public interface ExecutionStack extends Iterable<Statement> {
    void push(Statement statement);
    Statement pop();
    boolean isEmpty();
    String toString();
}
