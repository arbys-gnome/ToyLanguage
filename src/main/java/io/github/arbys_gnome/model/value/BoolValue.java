package io.github.arbys_gnome.model.value;

import io.github.arbys_gnome.model.type.Type;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean value) { this.value = value; }

    @Override
    public Type type() { return Type.BOOL; }

    @Override
    public Boolean value() { return value; }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }

    @Override
    public String toString() { return String.valueOf(value); }
}