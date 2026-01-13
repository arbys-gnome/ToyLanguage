package io.github.arbys_gnome.model.value;

import io.github.arbys_gnome.model.exception.InvalidDereferencingException;
import io.github.arbys_gnome.model.type.Type;

public interface Value {
    Type type();

    Object value();

    boolean equals(Object another);

    default Integer address() throws InvalidDereferencingException {
        if (!type().isReference()) {
            throw new InvalidDereferencingException("Can't dereference a " + type().toString() + " type.");
        }
        return null;
    }

    Value deepCopy();
}
