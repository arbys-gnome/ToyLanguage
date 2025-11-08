package me.rares.model.state;

import me.rares.model.type.Type;
import me.rares.model.value.Value;

public interface SymbolTable {
    void setValue(String variableName, Value value);

    boolean isDefined(String variableName);

    Type getType(String variableName);

    void declareVariable(Type type, String variableName);

    Value getValue(String variableName);
}
