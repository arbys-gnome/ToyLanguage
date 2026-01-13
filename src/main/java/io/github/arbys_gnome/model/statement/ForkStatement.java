package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.ExecutionStack;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.state.SymbolTable;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

public record ForkStatement(Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        SymbolTable newSymTable = state.symbolTable().deepCopy();

        ExecutionStack newStack = state.executionStack().createEmpty();
        // TODO: maybe directly push statement on the stack

        var heap = state.heap();
        var out = state.output();
        var fileTable = state.fileTable();

        ProgramState forked = new ProgramState(newStack, newSymTable, fileTable, heap, out, statement);

        return forked;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        // typecheck the forked statement in the current environment
        statement.typecheck(new HashMap<>(typeEnv));
        // return parent type environment unchanged
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
