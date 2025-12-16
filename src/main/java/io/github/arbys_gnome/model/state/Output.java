package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.value.Value;

public interface Output extends Iterable<Value> {
    void add(Value value);

    void clear();
}
