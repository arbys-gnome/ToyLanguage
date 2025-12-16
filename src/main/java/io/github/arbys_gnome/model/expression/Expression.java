package io.github.arbys_gnome.model.expression;

import io.github.arbys_gnome.model.state.Heap;
import io.github.arbys_gnome.model.state.SymbolTable;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception;
    Type typecheck(HashMap<String, Type> typeEnv) throws Exception;
}