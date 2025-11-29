package io.github.BogdanR6.model.value;

import io.github.BogdanR6.model.type.Type;

public class IntValue implements Value {
    private final int value;

    public IntValue(int value) { this.value = value; }

    @Override
    public Type type() { return Type.INT; }

    @Override
    public Integer value() { return value; }

    @Override
    public String toString() { return String.valueOf(value); }
}
