package me.rares.model.statement;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidTypeException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;
import me.rares.model.value.BoolValue;
import me.rares.model.value.Value;

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
