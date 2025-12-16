package io.github.arbys_gnome.model.expression;

import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.state.Heap;
import io.github.arbys_gnome.model.value.Value;
import io.github.arbys_gnome.model.state.SymbolTable;
import io.github.arbys_gnome.model.exception.UndefinedVariableException;

import java.util.HashMap;

public record VariableExpression(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws UndefinedVariableException {
        return symbolTable.getValue(variableName);
    }

    @Override
    public Type typecheck(HashMap<String, Type> typeEnv) throws Exception {
        if (!typeEnv.containsKey(variableName)) {
            throw new UndefinedVariableException("VariableExpression: Variable was not defined");
        }
        return typeEnv.get(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
