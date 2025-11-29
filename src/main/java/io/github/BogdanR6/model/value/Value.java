package io.github.BogdanR6.model.value;

import io.github.BogdanR6.model.exception.InvalidDereferencingException;
import io.github.BogdanR6.model.type.Type;

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
}
