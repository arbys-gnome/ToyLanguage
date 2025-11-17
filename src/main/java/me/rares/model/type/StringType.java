package me.rares.model.type;

import me.rares.model.value.StringValue;
import me.rares.model.value.Value;

public class StringType implements Type {
    @Override
    public Value defaultValue() { return new StringValue(""); }

    @Override
    public String toString() { return "string"; }

    @Override
    public boolean equals(Object another) { return  another instanceof StringType; }
}
