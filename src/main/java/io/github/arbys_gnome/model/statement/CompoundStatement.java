package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

public record CompoundStatement(Statement first, Statement second) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        // Make sure 'first' executes first
        state.executionStack().push(second);
        state.executionStack().push(first);
        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString();
    }
}
