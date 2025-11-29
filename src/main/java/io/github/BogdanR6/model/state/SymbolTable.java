package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.Value;

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
