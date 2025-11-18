package me.rares.model.type;

import me.rares.model.value.IntValue;
import me.rares.model.value.Value;

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
