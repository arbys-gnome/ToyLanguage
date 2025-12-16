package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.exception.UnallocatedAddressException;
import io.github.arbys_gnome.model.value.Value;

import java.util.Map;

public interface Heap {
    boolean isAllocated(Integer address);

    int allocate(Value value);

    Value read(Integer address) throws UnallocatedAddressException;

    void write(Integer address, Value value) throws UnallocatedAddressException;

    Iterable<Map.Entry<Integer, Value>> entrySet();

    void setContent(Map<Integer, Value> newContent);

    String toString();

    void clear();
}
