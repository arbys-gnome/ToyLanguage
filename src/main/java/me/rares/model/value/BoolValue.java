package me.rares.model.value;

import me.rares.model.type.Type;
import me.rares.model.type.BoolType;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean value) { this.value = value; }

    @Override
    public Type type() { return new BoolType(); }

    @Override
    public Object value() { return value; }

    @Override
    public String toString() { return String.valueOf(value); }
}