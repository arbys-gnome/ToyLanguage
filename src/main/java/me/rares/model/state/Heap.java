package me.rares.model.state;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.value.Value;

import java.util.Map;

public interface Heap {
    boolean isAllocated(Integer address);

    int allocate(Value value);

    Value read(Integer address) throws InvalidHeapAddressException;

    void write(Integer address, Value value) throws InvalidHeapAddressException;

    Iterable<Map.Entry<Integer, Value>> entrySet();

    void setContent(Map<Integer, Value> newContent);

    String toString();

    void clear();
}
