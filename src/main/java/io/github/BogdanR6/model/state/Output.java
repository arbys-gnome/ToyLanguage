package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.value.Value;

public interface Output extends Iterable<Value> {
    void add(Value value);

    void clear();
}
