package me.rares.model.state;

import me.rares.model.statement.Statement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListExecutionStack implements ExecutionStack {
    private final List<Statement> statements = new LinkedList<>();

    @Override
    public void push(Statement statement) {
        statements.addFirst(statement);
    }

    @Override
    public Statement pop() {
        return statements.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return statements.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ListExecutionStack:");
        if (isEmpty()) {
            sb.append("\n(empty)");
        }

        for (var statement : statements) {
            sb.append("\n").append(statement.toString());
        }

        return sb.toString();
    }

    @Override
    public void clear() {
        statements.clear();
    }

    @Override
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }
}
