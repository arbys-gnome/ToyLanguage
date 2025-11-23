package me.rares.model.statement;

import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;

public record PrintStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        state.getOutput().add(value);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
