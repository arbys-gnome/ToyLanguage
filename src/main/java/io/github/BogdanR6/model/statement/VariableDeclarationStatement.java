package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.exception.VariableRedefinitionException;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.type.Type;

public record VariableDeclarationStatement(Type type, String variableName) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws VariableRedefinitionException {
        var symbolTable = state.symbolTable();
        if (symbolTable.isDefined(variableName)) {
            throw new VariableRedefinitionException(variableName);
        }
        symbolTable.declareVariable(type, variableName);
        return state;
    }

    @Override
    public String toString() {
        return type.toString() + " " + variableName;
    }
}
