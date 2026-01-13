package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.exception.UndefinedVariableException;
import io.github.arbys_gnome.model.exception.VariableRedefinitionException;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.Value;

import java.util.Map;

public abstract class SymbolTable {
    public abstract boolean isDefined(String variableName);

    public final void declareVariable(Type type, String variableName) throws VariableRedefinitionException, InvalidVariableNameException {
        if (type == null) {
            throw new NullPointerException("Type is null");
        }
        if (variableName == null) {
            throw new NullPointerException("Variable name is null");
        }
        if (variableName.isEmpty()) {
            throw new InvalidVariableNameException("Variable name is empty");
        }
        if (isDefined(variableName)) {
            throw new VariableRedefinitionException(variableName);
        }
        declareVariableImpl(type, variableName);
    }

    protected abstract void declareVariableImpl(Type type, String variableName);

    public final Type getType(String variableName) throws UndefinedVariableException {
        if (!isDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        return getValue(variableName).type();
    }

    public final void setValue(String variableName, Value value) throws UndefinedVariableException {
        if (!isDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        setValueImpl(variableName, value);
    }

    protected abstract void setValueImpl(String variableName, Value value);

    public final Value getValue(String variableName) throws UndefinedVariableException {
        if (!isDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        return getValueImpl(variableName);
    }

    protected abstract Value getValueImpl(String variableName);

    public abstract Iterable<Map.Entry<String, Value>> entrySet();

    public abstract void clear();
    public abstract SymbolTable deepCopy();
}
