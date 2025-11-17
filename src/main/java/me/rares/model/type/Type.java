package me.rares.model.type;

import me.rares.model.value.Value;

public interface Type {

    Value defaultValue();

    String toString();

    boolean equals(Object other);
}
