package me.rares.model.expression;

import me.rares.model.state.ProgramState;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.Value;

public record RelationalExpression() implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        // TODO: Implement
        return null;
    }

}
