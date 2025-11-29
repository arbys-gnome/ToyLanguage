package io.github.BogdanR6.model.value;

import io.github.BogdanR6.model.type.Type;

public interface Value {
    Type type();

    Object value();

    boolean equals(Object another);
}
