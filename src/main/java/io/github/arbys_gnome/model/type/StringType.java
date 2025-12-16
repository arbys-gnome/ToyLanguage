package io.github.arbys_gnome.model.type;

import io.github.arbys_gnome.model.value.StringValue;
import io.github.arbys_gnome.model.value.Value;

class StringType implements Type {
    StringType() {}

    @Override
    public Value defaultValue() { return new StringValue(""); }

    @Override
    public String toString() { return "string"; }

    @Override
    public boolean equals(Object another) { return  another instanceof StringType; }

    @Override
    public boolean isReference() { return false; }
}
