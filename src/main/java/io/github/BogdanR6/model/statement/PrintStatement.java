package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;

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
