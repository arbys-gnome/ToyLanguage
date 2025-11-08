package me.rares.model.value;

import me.rares.model.type.Type;

public record BooleanValue(boolean value) implements Value {
    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }
}