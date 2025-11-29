package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.exception.UndefinedVariableException;
import io.github.BogdanR6.model.exception.VariableRedefinitionException;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.Value;

import java.util.Map;

public abstract class SymbolTable {
    public abstract boolean isDefined(String variableName);

    public final void declareVariable(Type type, String variableName) throws VariableRedefinitionException {
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
}
