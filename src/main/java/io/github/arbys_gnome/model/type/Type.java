package io.github.arbys_gnome.model.type;

import io.github.arbys_gnome.model.value.Value;

public interface Type {
    Type INT = new IntType();
    Type BOOL = new BoolType();
    Type STRING = new StringType();

    Value defaultValue();

    String toString();

    boolean equals(Object other);

    boolean isReference();
}
