package io.github.arbys_gnome.model.value;

import io.github.arbys_gnome.model.exception.InvalidDereferencingException;
import io.github.arbys_gnome.model.type.Type;

public class IntValue implements Value {
    private final int value;

    public IntValue(int value) { this.value = value; }

    @Override
    public Type type() { return Type.INT; }

    @Override
    public Integer value() { return value; }

    @Override
    public Integer address() throws InvalidDereferencingException {
        return 0;
    }

    @Override
    public String toString() { return String.valueOf(value); }
}
