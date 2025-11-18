package me.rares.model.state;

import me.rares.model.value.Value;

public interface Heap {
    int allocate(Value value);

    String toString();
}
