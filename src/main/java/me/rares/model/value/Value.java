package me.rares.model.value;

import me.rares.model.type.Type;

public interface Value {
    Type type();

    boolean equals(Object another);
}
