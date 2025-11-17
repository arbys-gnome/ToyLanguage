package me.rares.model.value;

import me.rares.model.type.Type;
import me.rares.model.type.StringType;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) { this.value = value; }

    @Override
    public Type type() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "'" + String.valueOf(value) + "'";
    }
}
