package me.rares.model.state;

import me.rares.model.value.Value;

public interface Output extends Iterable<Value> {
    void add(Value value);

    void clear();
}
