package io.github.arbys_gnome.model.expression;

import io.github.arbys_gnome.model.state.Heap;
import io.github.arbys_gnome.model.state.SymbolTable;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public record ValueExpression(Value value) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        return value;
    }

    @Override
    public Type typecheck(HashMap<String, Type> typeEnv) {
        return value.type();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
