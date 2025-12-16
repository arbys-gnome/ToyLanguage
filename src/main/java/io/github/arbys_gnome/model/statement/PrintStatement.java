package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;

import java.util.HashMap;

public record PrintStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        state.output().add(value);
        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        try {
            expression.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
