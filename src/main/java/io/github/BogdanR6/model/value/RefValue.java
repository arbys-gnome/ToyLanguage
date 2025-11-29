package io.github.BogdanR6.model.value;

import io.github.BogdanR6.model.type.RefType;
import io.github.BogdanR6.model.type.Type;

public class RefValue implements Value {
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type location) {
        this.address = address;
        this.locationType = location;
    }

    public int address() { return (int)this.value(); }

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
