package me.rares.model.type;

import me.rares.model.value.Value;

public interface Type {
    Type INT = new IntType();
    Type BOOL = new BoolType();
    Type STRING = new StringType();

    Value defaultValue();

    String toString();

    boolean equals(Object other);

    boolean isReference();
}
