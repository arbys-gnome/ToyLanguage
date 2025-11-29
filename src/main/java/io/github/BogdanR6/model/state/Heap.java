package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.exception.InvalidHeapAddressException;
import io.github.BogdanR6.model.value.Value;

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
