package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.BoolValue;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public record IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        Value value = condition.evaluate(state.symbolTable(), state.heap());
        if (!value.type().equals(Type.BOOL)) {
            throw new InvalidTypeException("The condition must evaluate to bool");
        }

        var booleanValue = (BoolValue) value;
        if (booleanValue.value()) {
            state.executionStack().push(thenStatement);
        } else {
            state.executionStack().push(elseStatement);
        }
        return null;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        Type expType = null;
        try {
            expType = condition.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (expType.equals(Type.BOOL)) {
            thenStatement.typecheck(new HashMap<>(typeEnv));
            elseStatement.typecheck(new HashMap<>(typeEnv));
            return typeEnv;
        } else {
            throw new InvalidTypeException("IfStatement: Condition must evaluate to bool type.");
        }
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ") {" + thenStatement.toString() + "} else {" + elseStatement.toString() + "}";
    }
}
