package io.github.BogdanR6.model.type;

import io.github.BogdanR6.model.value.Value;

public interface Type {
    Type INT = new IntType();
    Type BOOL = new BoolType();
    Type STRING = new StringType();

    Value defaultValue();

    String toString();

    boolean equals(Object other);

    boolean isReference();
}
