package me.rares.model.type;

import me.rares.model.value.Value;

public interface Type {
    // INTEGER,
    // BOOLEAN,
    // STRING,
    // REFERENCE;

    Value defaultValue();

    String toString();

    boolean equals(Object other);
}
