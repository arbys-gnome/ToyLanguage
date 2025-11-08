package me.rares.model.statement;

import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;

public record VariableDeclarationStatement(Type type, String variableName) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.symbolTable();
        if (symbolTable.isDefined(variableName)) {
            throw new RuntimeException("Variable " + variableName + " is already defined");
        }
        symbolTable.declareVariable(type, variableName);
        return state;
    }
}
