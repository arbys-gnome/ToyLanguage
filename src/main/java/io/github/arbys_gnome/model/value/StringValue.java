package io.github.arbys_gnome.model.value;

import io.github.arbys_gnome.model.type.Type;

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
