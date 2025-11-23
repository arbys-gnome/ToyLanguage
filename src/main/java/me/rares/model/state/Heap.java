package me.rares.model.state;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.value.Value;

public interface Heap {
    boolean isAllocated(Integer address);

    int allocate(Value value);

    Value read(Integer address);

    void write(Integer address, Value value) throws InvalidHeapAddressException;

    String toString();
}
