package io.github.BogdanR6.model.value;

import io.github.BogdanR6.model.exception.InvalidDereferencingException;
import io.github.BogdanR6.model.exception.InvalidVariableTypeException;
import io.github.BogdanR6.model.type.RefType;
import io.github.BogdanR6.model.type.Type;

public record StringValue(String value) implements Value {

    @Override
    public Type type() {
        return Type.STRING;
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }
}
