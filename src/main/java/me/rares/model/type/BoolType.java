package me.rares.model.type;

import me.rares.model.value.BoolValue;
import me.rares.model.value.Value;

public class BoolType implements Type {
    @Override
    public Value defaultValue() { return new BoolValue(false); }

    @Override
    public String toString() { return "bool"; }

    @Override
    public boolean equals(Object another) { return another instanceof BoolType; }
}
