package me.rares.model.statement;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidTypeException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;

public record PrintStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        state.output().add(value);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
