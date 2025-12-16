package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.BoolValue;
import io.github.arbys_gnome.model.value.Value;

public record WhileStatement(Expression condition, Statement body) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        Value conditionVal = condition.evaluate(state.symbolTable(), state.heap());
        if (conditionVal.type().equals(Type.BOOL)) {
            throw new InvalidTypeException("The condition of while should evaluate to a boolean value.");
        }
        BoolValue conditionBoolVal = (BoolValue) conditionVal;
        if (conditionBoolVal.value()) {
            state.executionStack().push(this);
            state.executionStack().push(body);
        }
        return state;
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ") { " + body.toString() + " }";
    }
}
