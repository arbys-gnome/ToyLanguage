package me.rares.model.type;

import me.rares.model.value.BooleanValue;
import me.rares.model.value.IntegerValue;
import me.rares.model.value.StringValue;
import me.rares.model.value.Value;

public enum Type {
    INTEGER,
    BOOLEAN,
    STRING;

    public Value getDefaultValue() {
        return switch (this) {
            case INTEGER -> new IntegerValue(0);
            case BOOLEAN -> new BooleanValue(false);
            case STRING -> new StringValue("");
        };
    }
}
