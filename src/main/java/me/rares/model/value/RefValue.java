package me.rares.model.value;

import me.rares.model.type.RefType;
import me.rares.model.type.Type;

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
    public Object value() { return address; }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue)
            return address == ((RefValue) another).address();
        else return false;
    }
}
