package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.BoolValue;
import io.github.BogdanR6.model.value.Value;

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
