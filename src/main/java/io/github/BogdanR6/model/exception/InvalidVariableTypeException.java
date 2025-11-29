package io.github.BogdanR6.model.exception;

import io.github.BogdanR6.model.type.Type;

public class InvalidVariableTypeException extends Exception {
    public InvalidVariableTypeException(String variableName, Type expectedType) {
        super(variableName + " is not of the expected type: " + expectedType.toString());
    }
}