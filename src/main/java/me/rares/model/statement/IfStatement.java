package me.rares.model.statement;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;
import me.rares.model.value.BoolValue;
import me.rares.model.value.Value;

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
    public String toString() {
        return "if(" + condition.toString() + ") {" + thenStatement.toString() + "} else {" + elseStatement.toString() + "}";
    }
}
