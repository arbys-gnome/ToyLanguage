package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.exception.VariableRedefinitionException;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

public record VariableDeclarationStatement(Type type, String variableName) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws VariableRedefinitionException, Exception {
        var symbolTable = state.symbolTable();
        if (symbolTable.isDefined(variableName)) {
            throw new VariableRedefinitionException(variableName);
        }
        symbolTable.declareVariable(type, variableName);
        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        typeEnv.put(variableName, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + variableName;
    }
}
