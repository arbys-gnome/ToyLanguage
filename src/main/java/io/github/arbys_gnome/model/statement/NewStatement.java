package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.RefType;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.RefValue;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public record NewStatement(String var, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        if (!state.symbolTable().isDefined(var)) {
            throw new InvalidVariableNameException(var + " is not defined.");
        }

        if (!state.symbolTable().getType(var).isReference()) {
            throw new InvalidTypeException(var + " is not reference.");
        }
        RefValue ref = (RefValue)state.symbolTable().getValue(var);
        Value value = expression.evaluate(state.symbolTable(), state.heap());
        if (!value.type().equals(((RefType)ref.type()).innerType())) {
            throw new InvalidTypeException("Can't assign a value of type " + value.type() + " to a reference of type " + ref.type() + ".");
        }

        int address = state.heap().allocate(value);

        state.symbolTable().setValue(var, new RefValue(address, value.type()));

        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        if (!typeEnv.containsKey(var)) {
            throw new InvalidTypeException("Variable " + var + " is not defined");
        }
        Type varType = typeEnv.get(var);
        Type expType = null;
        try {
            expType = expression.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (varType.equals(new RefType(expType))) {
            return typeEnv;
        } else {
            throw new InvalidTypeException("NewStatement: right hand side and left hand side have different types");
        }
    }

    @Override
    public String toString() {
        return "new";
    }
}
