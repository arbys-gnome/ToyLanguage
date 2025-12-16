package io.github.arbys_gnome.model.type;

import io.github.arbys_gnome.model.value.RefValue;
import io.github.arbys_gnome.model.value.Value;

public class RefType implements Type {
    private final Type inner;

    public RefType(Type inner) { this.inner = inner; }

    public Type innerType() { return this.inner; }

    @Override
    public Value defaultValue() { return new RefValue(0,inner); }

    @Override
    public String toString() { return "&" + inner.toString(); }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType)
            return inner.equals(((RefType)another).innerType());
        else return false;
    }

    @Override
    public boolean isReference() { return true; }
}
