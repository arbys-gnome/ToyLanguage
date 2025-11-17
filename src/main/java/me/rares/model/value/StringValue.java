package me.rares.model.value;

import me.rares.model.type.Type;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) { this.value = value; }

    @Override
    public Type type() { return Type.STRING; }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "'" + String.valueOf(value) + "'";
    }
}
