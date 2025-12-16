package io.github.arbys_gnome.model.exception;

import io.github.arbys_gnome.model.type.Type;

public class InvalidVariableTypeException extends Exception {
    public InvalidVariableTypeException(String variableName, Type expectedType) {
        super(variableName + " is not of the expected type: " + expectedType.toString());
    }
}