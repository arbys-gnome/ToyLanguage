package me.rares.model.value;

import me.rares.model.type.Type;

public record IntegerValue(int value) implements Value {
    @Override
    public Type getType() {
        return Type.INTEGER;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
