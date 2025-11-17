package me.rares.model.value;

import me.rares.model.type.Type;

public interface Value {
    Type type();

    Object value();

    boolean equals(Object another);
}
