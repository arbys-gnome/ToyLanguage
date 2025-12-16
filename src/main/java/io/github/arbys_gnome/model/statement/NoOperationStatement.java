package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

public class NoOperationStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NoOp";
    }
}
