package io.github.arbys_gnome.model.type;

import io.github.arbys_gnome.model.value.IntValue;
import io.github.arbys_gnome.model.value.Value;

final class IntType implements Type {
    IntType() {}

    @Override
    public Value defaultValue() { return new IntValue(0); }

    @Override
    public String toString() { return "int"; }

    @Override
    public boolean equals(Object another) { return another instanceof IntType; }

    @Override
    public boolean isReference() { return false; }
}
