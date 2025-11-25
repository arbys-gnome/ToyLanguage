package me.rares.model.state;

import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.type.Type;
import me.rares.model.value.Value;

import java.util.Map;

public interface SymbolTable {
    void setValue(String variableName, Value value);

    boolean isDefined(String variableName);

    Type getType(String variableName);

    void declareVariable(Type type, String variableName);

    Value getValue(String variableName);

    Value lookup(String variableName) throws InvalidVariableNameException;

    Iterable<Map.Entry<String, Value>> entrySet();

    void clear();
}
