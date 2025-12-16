package io.github.arbys_gnome.model.type;

import io.github.arbys_gnome.model.value.BoolValue;
import io.github.arbys_gnome.model.value.Value;

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
