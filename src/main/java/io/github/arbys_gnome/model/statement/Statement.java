package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

public interface Statement {
    ProgramState execute(ProgramState state) throws Exception;
    HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException;
    String toString();
}
