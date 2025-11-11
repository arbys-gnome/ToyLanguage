package me.rares.model.value;

import me.rares.model.type.Type;

public record StringValue(String value) implements Value {
    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public String toString() {
        return "'" + String.valueOf(value) + "'";
    }
}
