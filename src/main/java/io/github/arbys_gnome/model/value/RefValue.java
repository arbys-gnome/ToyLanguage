package io.github.arbys_gnome.model.value;

import io.github.arbys_gnome.model.type.RefType;
import io.github.arbys_gnome.model.type.Type;

public class RefValue implements Value {
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type location) {
        this.address = address;
        this.locationType = location;
    }

    @Override
    public Integer address() { return (int)this.value(); }

    @Override
    public Type type() { return new RefType(locationType); }

    @Override
    public Integer value() { return address; }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue)
            return address == ((RefValue) another).address();
        else return false;
    }

    @Override
    public String toString() {
        return "&" + address + ":" + locationType;
    }
}
