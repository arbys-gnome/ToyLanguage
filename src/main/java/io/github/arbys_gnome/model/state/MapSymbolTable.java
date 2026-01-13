package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.exception.UndefinedVariableException;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable extends SymbolTable {
    private final Map<String, Value> content = new HashMap<>();

    @Override
    public boolean isDefined(String variableName) {
        return content.containsKey(variableName);
    }

    @Override
    public void setValueImpl(String variableName, Value value) {
        content.put(variableName, value);
    }

    @Override
    public void declareVariableImpl(Type type, String variableName) {
        content.put(variableName, type.defaultValue());
    }

    @Override
    public Value getValueImpl(String variableName) {
        return content.get(variableName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MapSymbolTable:");
        if (content.isEmpty()) {
            return sb.append("\n(empty)").toString();
        }

        for (var entry : content.entrySet()) {
            sb.append("\n").append(entry.getKey()).append(": ").append(entry.getValue());
        }

        return sb.toString();
    }

    @Override
    public Iterable<Map.Entry<String, Value>> entrySet() {
        return content.entrySet();
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public SymbolTable deepCopy() {
        MapSymbolTable copy = new MapSymbolTable();
        for (var entry : content.entrySet()) {
            try {
                copy.setValue(entry.getKey(), entry.getValue().deepCopy());
            } catch (UndefinedVariableException e) {
                // TODO: better handle this
                throw new RuntimeException(e);
            }
        }
        return copy;
    }
}
