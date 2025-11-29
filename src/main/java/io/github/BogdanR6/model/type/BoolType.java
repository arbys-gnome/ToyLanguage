package io.github.BogdanR6.model.type;

import io.github.BogdanR6.model.value.BoolValue;
import io.github.BogdanR6.model.value.Value;

final class BoolType implements Type {
    BoolType() {}

    @Override
    public Value defaultValue() { return new BoolValue(false); }

    @Override
    public String toString() { return "bool"; }

    @Override
    public boolean equals(Object another) { return another instanceof BoolType; }

    @Override
    public boolean isReference() { return false; }
}
